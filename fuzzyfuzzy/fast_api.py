import os
import pandas as pd
from fuzzywuzzy import fuzz, process
from sentence_transformers import SentenceTransformer, util
import torch
from fastapi import FastAPI
from pydantic import BaseModel

# -------------------------------
# Load Data Once (Global)
# -------------------------------
BASE_DIR = os.path.dirname(os.path.abspath(__file__))
namaste_path = os.path.join(BASE_DIR, "NAMASTE_sampled_100_each.xlsx")
icd_path = os.path.join(BASE_DIR, "SimpleTabulation-ICD-11-MMS-en.csv")

namaste_df = pd.read_excel(namaste_path).fillna("")
icd_df = pd.read_csv(icd_path).fillna("")
# Precompute embeddings once for ICD
model = SentenceTransformer("all-MiniLM-L6-v2")
icd_df["description"] = icd_df["Title"].fillna("").str.strip()
icd_embeddings = model.encode(icd_df["description"].tolist(), convert_to_tensor=True)

# -------------------------------
# Utility Functions
# -------------------------------
def fuzzy_map(term, threshold=80):
    match, score = process.extractOne(term.lower(), icd_df["Title"].str.lower().str.strip().tolist(), scorer=fuzz.token_sort_ratio)
    if match and score >= threshold:
        icd_code = icd_df.loc[icd_df["Title"].str.lower().str.strip() == match, "Code"].values[0]
        return {"ICD_match": match, "ICD_code": icd_code, "Confidence": f"{score}% (Fuzzy)"}
    return None

def semantic_map(term, threshold=0.7):
    term_embedding = model.encode([term], convert_to_tensor=True)
    sims = util.cos_sim(term_embedding, icd_embeddings)[0]
    best_idx = torch.argmax(sims).item()
    score = sims[best_idx].item()
    if score >= threshold:
        return {
            "ICD_match": icd_df.iloc[best_idx]["Title"],
            "ICD_code": icd_df.iloc[best_idx]["Code"],
            "Confidence": f"{round(score*100, 1)}% (Semantic)"
        }
    return None

# -------------------------------
# FastAPI App
# -------------------------------
app = FastAPI(title="NAMASTE → ICD Mapping API")

class TermRequest(BaseModel):
    term: str

@app.post("/map")
def map_term(req: TermRequest):
    term = req.term.strip()

    # Step 1: Try fuzzy
    result = fuzzy_map(term)

    # Step 2: If no fuzzy match, try semantic
    if not result:
        result = semantic_map(term)

    if result:
        return {"NAMASTE_term": term, **result}
    else:
        return {"NAMASTE_term": term, "message": "No reliable match found"}


