import os
import pandas as pd
from fuzzywuzzy import fuzz, process
from sentence_transformers import SentenceTransformer, util
import torch

# -------------------------------
# Load Data Once (Global)
# -------------------------------
BASE_DIR = os.path.dirname(os.path.abspath(__file__))
namaste_path = os.path.join(BASE_DIR, "NAMASTE_sampled_100_each.xlsx")
icd_path = os.path.join(BASE_DIR, "SimpleTabulation-ICD-11-MMS-en.csv")

namaste_df = pd.read_excel(namaste_path).fillna("")
icd_df = pd.read_csv(icd_path).fillna("")

# -------------------------------
# Preprocess & Clean
# -------------------------------
namaste_df.columns = namaste_df.columns.str.strip()
icd_df.columns = icd_df.columns.str.strip()

# Rename NAMC_TERM to term for convenience
if "NAMC_TERM" in namaste_df.columns:
    namaste_df.rename(columns={"NAMC_TERM": "term"}, inplace=True)

# Ensure ICD CSV has required columns
if "Title" not in icd_df.columns or "Code" not in icd_df.columns:
    raise ValueError("ICD CSV must have 'Title' and 'Code' columns")

# Precompute semantic embeddings
model = SentenceTransformer("all-MiniLM-L6-v2")
icd_df["description"] = icd_df["Title"].fillna("").str.strip()
icd_embeddings = model.encode(icd_df["description"].tolist(), convert_to_tensor=True)

# -------------------------------
# Mapping Utilities
# -------------------------------
def fuzzy_map(text, threshold=80):
    """Fuzzy match NAMASTE text against ICD Titles"""
    match, score = process.extractOne(
        text.lower().strip(),
        icd_df["Title"].str.lower().str.strip().tolist(),
        scorer=fuzz.token_sort_ratio
    )
    if match and score >= threshold:
        icd_code = icd_df.loc[
            icd_df["Title"].str.lower().str.strip() == match, "Code"
        ].values[0]
        return {"ICD_term": match, "ICD_code": icd_code, "Confidence": f"{score}% (Fuzzy)"}
    return None

def semantic_map(text, threshold=0.7):
    """Semantic embedding similarity between NAMASTE text and ICD Titles"""
    embedding = model.encode([text], convert_to_tensor=True)
    sims = util.cos_sim(embedding, icd_embeddings)[0]
    best_idx = torch.argmax(sims).item()
    score = sims[best_idx].item()
    if score >= threshold:
        return {
            "ICD_term": icd_df.iloc[best_idx]["Title"],
            "ICD_code": icd_df.iloc[best_idx]["Code"],
            "Confidence": f"{round(score*100,1)}% (Semantic)"
        }
    return None

# -------------------------------
# Main Mapping Function
# -------------------------------
def map_namaste(namc_code: str = None, namc_term: str = None):
    """Map NAMC code or NAMC term to ICD code & term"""
    if not namc_code and not namc_term:
        return {"error": "Provide either NAMC_CODE or NAMC_TERM"}

    # Step 1: Determine the NAMASTE row
    if namc_code:
        if "NAMC_CODE" not in namaste_df.columns:
            return {"error": "NAMASTE dataset does not have 'NAMC_CODE'"}
        row = namaste_df[namaste_df["NAMC_CODE"] == namc_code]
        if row.empty:
            return {"error": f"NAMC_CODE '{namc_code}' not found"}
        row = row.iloc[0]
    else:  # lookup by term
        term = namc_term.strip()
        row = namaste_df[namaste_df["term"].str.lower() == term.lower()]
        if row.empty:
            return {"error": f"NAMC_TERM '{term}' not found"}
        row = row.iloc[0]

    # Step 2: Choose mapping text
    if "TYPE" in row and str(row["TYPE"]).strip().lower() == "ayurveda":
        mapping_text = str(row.get("Long_definition", row.get("Short_definition")))
    else:
        mapping_text = str(row.get("Short_definition"))

    if not mapping_text:
        return {
            "NAMC_CODE": row.get("NAMC_CODE", None),
            "NAMC_TERM": row.get("term", None),
            "message": "No definition available for mapping"
        }

    # Step 3: Map using fuzzy or semantic
    result = fuzzy_map(mapping_text) or semantic_map(mapping_text)

    if result:
        return {
            "NAMC_CODE": row.get("NAMC_CODE", None),
            "NAMC_TERM": row.get("term", None),
            "ICD_code": result["ICD_code"],
            "ICD_term": result["ICD_term"],
        }

    return {
        "NAMC_CODE": row.get("NAMC_CODE", None),
        "NAMC_TERM": row.get("term", None),
        "message": "No reliable ICD match found"
    }
