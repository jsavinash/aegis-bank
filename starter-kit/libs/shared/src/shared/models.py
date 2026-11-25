from pydantic import BaseModel

class Money(BaseModel):
    amount: str
    currency: str = 'USD'
