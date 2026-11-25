package com.aegis.credit.domain;

/**
 * Credit score band derived from a numeric score.
 *
 * <p>Thresholds are inclusive at the lower bound: EXCELLENT {@code >= 740}, GOOD {@code >= 670},
 * FAIR {@code >= 580}, otherwise POOR.
 */
public enum CreditBand {
  EXCELLENT,
  GOOD,
  FAIR,
  POOR;

  public static CreditBand fromScore(int score) {
    if (score >= 740) {
      return EXCELLENT;
    }
    if (score >= 670) {
      return GOOD;
    }
    if (score >= 580) {
      return FAIR;
    }
    return POOR;
  }
}