from fastapi import FastAPI
from pydantic import BaseModel
from typing import Optional
import re

app = FastAPI()


class GiftTextRequest(BaseModel):
    text: str


class ParsedGiftResponse(BaseModel):
    relationType: Optional[str] = None
    specialDay: Optional[str] = None
    color: Optional[str] = None
    maxBudget: Optional[int] = None
    personalized: Optional[bool] = None


@app.get("/health")
def health():
    return {"status": "ok"}



RELATION_KEYWORDS = {
    "Anne": ["anne", "annem"],
    "Baba": ["baba", "babam"],
    "Sevgili": ["sevgili"],
    "Eş": ["eş"],
    "Arkadaş": ["arkadaş"]
}

SPECIAL_DAY_KEYWORDS = {
    "Doğum Günü": ["doğum günü"],
    "Sevgililer Günü": ["sevgililer günü"],
    "Anneler Günü": ["anneler günü"]
}

COLOR_KEYWORDS = {
    "kırmızı": ["kırmızı"],
    "mor": ["mor"],
    "mavi": ["mavi"],
    "siyah": ["siyah"],
    "beyaz": ["beyaz"]
}

PERSONALIZED_KEYWORDS = ["kişiye özel", "isimli", "özel tasarım"]


def extract_from_keywords(text, mapping):
    for key, words in mapping.items():
        for w in words:
            if w in text:
                return key
    return None


def extract_budget(text):
    match = re.search(r"(\d{2,6})\s*(tl|₺|lira)?", text)
    return int(match.group(1)) if match else None


def extract_personalized(text):
    return any(w in text for w in PERSONALIZED_KEYWORDS)


@app.post("/ai-recommend", response_model=ParsedGiftResponse)
def ai_recommend(req: GiftTextRequest):
    text = req.text.lower()

    return ParsedGiftResponse(
        relationType=extract_from_keywords(text, RELATION_KEYWORDS),
        specialDay=extract_from_keywords(text, SPECIAL_DAY_KEYWORDS),
        color=extract_from_keywords(text, COLOR_KEYWORDS),
        maxBudget=extract_budget(text),
        personalized=extract_personalized(text)
    )
