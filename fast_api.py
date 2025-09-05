# fast_api.py
from fastapi import FastAPI, Query
from namaste_map import map_namaste

app = FastAPI(title="NAMASTE → ICD Mapping API")

@app.get("/map")
def map_code_or_term(
    namc_code: str = Query(None, description="Provide NAMC Code"),
    namc_term: str = Query(None, description="Provide NAMC Term"),
):
    """
    Map NAMC_CODE or NAMC_TERM to ICD code & title.
    """
    result = map_namaste(namc_code=namc_code, namc_term=namc_term)
    return result
