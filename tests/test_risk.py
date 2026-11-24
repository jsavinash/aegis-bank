from aegis.risk import HotPathScorer, RiskSignals, decide, route


def test_low_risk_approves():
    s = RiskSignals(amount=50, velocity_1h=1, device_age_days=900, device_shared_count=0, merchant_risk=0.1)
    sc = HotPathScorer().score(s)
    assert decide(sc).value == "approve"
    assert route(sc) == "hot"


def test_high_velocity_steps_up():
    s = RiskSignals(amount=1840, velocity_1h=3, device_age_days=5, device_shared_count=4, merchant_risk=0.7)
    sc = HotPathScorer().score(s)
    assert sc.score >= 0.4
    assert route(sc) in ("warm", "hot_decline")
    assert decide(sc).value in ("step_up", "decline")


def test_device_farm_declines():
    s = RiskSignals(amount=5000, velocity_1h=8, device_age_days=1, device_shared_count=10, merchant_risk=0.9, prior_chargebacks=3)
    sc = HotPathScorer().score(s)
    assert sc.score >= 0.7
    assert sc.level in ("high", "critical")


def test_drivers_populated():
    s = RiskSignals(amount=1840, velocity_1h=5, device_age_days=2, device_shared_count=3)
    sc = HotPathScorer().score(s)
    assert "high_velocity" in sc.drivers or "new_device" in sc.drivers