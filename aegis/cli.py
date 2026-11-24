"""Aegis CLI: serve, investigate, score, eval, seed."""

from __future__ import annotations

import asyncio

import typer
from rich import print as rprint
from rich.table import Table

from aegis import __version__

app = typer.Typer(help="Aegis — AI-native financial crime compliance platform", no_args_is_help=True)


@app.command()
def serve(host: str = "0.0.0.0", port: int = 8000, reload: bool = True):
    """Run the API server."""
    import uvicorn

    uvicorn.run("aegis.api:app", host=host, port=port, reload=reload)


@app.command()
def investigate(alert: str = "structuring", severity: str = "high"):
    """Run an AML investigation on a simulated alert and print the case."""
    from aegis.models import AlertType, RiskLevel
    from aegis.workflows import make_case, run_local

    case = make_case(AlertType(alert), RiskLevel(severity))
    case = asyncio.run(run_local(case))
    _print_case(case)


@app.command()
def score(
    amount: float = 1840.0,
    velocity: int = 3,
    device_age: int = 5,
    shared: int = 4,
    merchant: float = 0.7,
):
    """Score a transaction on the hot path."""
    from aegis.risk import HotPathScorer, RiskSignals, decide, route

    s = RiskSignals(
        amount=amount,
        velocity_1h=velocity,
        device_age_days=device_age,
        device_shared_count=shared,
        merchant_risk=merchant,
    )
    sc = HotPathScorer().score(s)
    rprint(f"[bold]score[/bold]      {sc.score:.3f} ({sc.level.value})")
    rprint(f"[bold]decision[/bold]   {decide(sc).value}")
    rprint(f"[bold]route[/bold]      {route(sc)}")
    rprint(f"[bold]drivers[/bold]    {', '.join(sc.drivers) or 'none'}")


@app.command()
def eval(suite: str = "smoke"):
    """Run an eval suite and report the CI gate."""
    from aegis.eval import build_smoke_suite

    s = build_smoke_suite()
    from aegis.agents import run_pipeline
    from aegis.models import Alert, AlertType, CaseState, Entity, RiskLevel

    async def ans(q: str) -> str:
        c = CaseState(
            entity=Entity(legal_name="Acme"),
            alert=Alert(alert_type=AlertType.STRUCTURING, entity_id=__import__("uuid").uuid4(), severity=RiskLevel.HIGH, score=0.8),
        )
        await run_pipeline(c)
        return c.narrative or "no narrative"

    result = s.run(lambda q: asyncio.run(ans(q)))
    passed = s.gate(result)
    table = Table(title=f"Eval: {suite}")
    table.add_column("id")
    table.add_column("ok")
    table.add_column("score")
    for r in result["results"]:
        table.add_row(r["id"], "PASS" if r["ok"] else "FAIL", f"{r['score']:.2f}")
    rprint(table)
    rprint(f"accuracy={result['accuracy']} gate={'PASS' if passed else 'FAIL'}")


@app.command()
def seed():
    """Print a sample alert/case."""
    from aegis.workflows import make_case

    case = make_case()
    rprint(case.model_dump_json(indent=2))


def _print_case(case):
    table = Table(title=f"Case {case.case_id}")
    table.add_column("field")
    table.add_column("value")
    table.add_row("status", case.status.value)
    table.add_row("entity", case.entity.legal_name if case.entity else "-")
    table.add_row("evidence", str(len(case.evidence)))
    table.add_row("hypotheses", ", ".join(h.code for h in case.hypotheses) or "-")
    table.add_row("risk_score", f"{case.risk_score:.2f}")
    rprint(table)
    if case.narrative:
        rprint("[bold]narrative[/bold]")
        rprint(case.narrative)


@app.command()
def version():
    rprint(f"aegis {__version__}")


if __name__ == "__main__":
    app()