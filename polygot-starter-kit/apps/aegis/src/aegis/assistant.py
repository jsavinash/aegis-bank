from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI(title="Aegis Assistant")


class Query(BaseModel):
    text: str
    customer_id: str | None = None


ESCALATE = {"card lost", "lost", "fraud", "dispute", "unauthorised"}


@app.get("/health")
def health():
    return {"ok": True}


@app.post("/assistant/query")
def query(q: Query):
    t = q.text.lower()
    if any(k in t for k in ESCALATE):
        return {"escalate": True, "reason": "human_review", "text": q.text}
    return {"escalate": False, "answer": f"Here's help with: {q.text}"}
