package com.aegis.credit;

import static org.junit.jupiter.api.Assertions.*;

import com.aegis.credit.command.UpsertProfile;
import com.aegis.credit.domain.CreditBand;
import com.aegis.credit.domain.CreditProfile;
import com.aegis.credit.error.CreditProfileNotFound;
import com.aegis.credit.web.CreditProfileView;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CreditServiceTest {

  private final CreditService service = new CreditService();

  @Test
  void bandingThresholds() {
    assertEquals(CreditBand.EXCELLENT, CreditBand.fromScore(740));
    assertEquals(CreditBand.EXCELLENT, CreditBand.fromScore(850));
    assertEquals(CreditBand.GOOD, CreditBand.fromScore(739));
    assertEquals(CreditBand.GOOD, CreditBand.fromScore(670));
    assertEquals(CreditBand.FAIR, CreditBand.fromScore(669));
    assertEquals(CreditBand.FAIR, CreditBand.fromScore(580));
    assertEquals(CreditBand.POOR, CreditBand.fromScore(579));
    assertEquals(CreditBand.POOR, CreditBand.fromScore(300));
  }

  @Test
  void upsertCreatesAndDerivesBand() {
    CreditProfileView view = service.upsert(new UpsertProfile("c1", 700));
    assertEquals("c1", view.customerId());
    assertEquals(700, view.score());
    assertEquals(CreditBand.GOOD, view.band());
    assertEquals(0, view.version());
  }

  @Test
  void upsertReplacesByCustomerKeepingId() {
    CreditProfileView first = service.upsert(new UpsertProfile("c1", 700));
    CreditProfileView second = service.upsert(new UpsertProfile("c1", 760));
    assertEquals(first.id(), second.id());
    assertEquals(760, second.score());
    assertEquals(CreditBand.EXCELLENT, second.band());
    assertEquals(1, second.version());
    assertEquals(1, service.list().size());
    assertTrue(service.getByCustomer("c1").isPresent());
  }

  @Test
  void scoreBelowRangeIsRejected() {
    assertThrows(IllegalArgumentException.class, () -> service.upsert(new UpsertProfile("c1", 299)));
  }

  @Test
  void scoreAboveRangeIsRejected() {
    assertThrows(IllegalArgumentException.class, () -> service.upsert(new UpsertProfile("c1", 851)));
  }

  @Test
  void entityRejectsOutOfRangeScore() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new CreditProfile(UUID.randomUUID(), "c1", 900, null, 0L, Instant.now()));
  }

  @Test
  void withScoreRecomputesBand() {
    CreditProfile base = new CreditProfile(UUID.randomUUID(), "c1", 700, null, 0L, Instant.now());
    CreditProfile simulated = base.withScore(500);
    assertEquals(500, simulated.score());
    assertEquals(CreditBand.POOR, simulated.band());
    assertEquals(700, base.score());
    assertEquals(CreditBand.GOOD, base.band());
  }

  @Test
  void simulateClampsToRange() {
    assertEquals(850, service.simulate(700, 1000));
    assertEquals(300, service.simulate(400, -1000));
    assertEquals(650, service.simulate(700, -50));
  }

  @Test
  void getUnknownThrowsNotFound() {
    assertThrows(CreditProfileNotFound.class, () -> service.get(UUID.randomUUID()));
  }

  @Test
  void upsertPersistsUpdatedScore() {
    CreditProfileView first = service.upsert(new UpsertProfile("c9", 700));
    CreditProfileView second = service.upsert(new UpsertProfile("c9", 800));
    // The store must reflect the update, not just the returned view.
    CreditProfileView fetched = service.get(second.id());
    assertEquals(800, fetched.score());
    assertEquals(CreditBand.EXCELLENT, fetched.band());
    assertEquals(1, fetched.version());
    assertEquals(800, service.getByCustomer("c9").orElseThrow().score());
  }
}