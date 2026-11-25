package com.aegis.cards.domain;

import java.math.BigDecimal;

/**
 * Per-channel card controls and spend limits. Each toggle independently enables/disables a channel;
 * a {@code null} limit means "no limit". Controls are only enforced while the card is ACTIVE.
 */
public final class CardControls {

  private boolean online = true;
  private boolean contactless = true;
  private boolean atm = true;
  private boolean international = false;
  private BigDecimal dailyLimit;
  private BigDecimal perTransactionLimit;

  public boolean online() {
    return online;
  }

  public boolean contactless() {
    return contactless;
  }

  public boolean atm() {
    return atm;
  }

  public boolean international() {
    return international;
  }

  public BigDecimal dailyLimit() {
    return dailyLimit;
  }

  public BigDecimal perTransactionLimit() {
    return perTransactionLimit;
  }

  public void setOnline(boolean value) {
    this.online = value;
  }

  public void setContactless(boolean value) {
    this.contactless = value;
  }

  public void setAtm(boolean value) {
    this.atm = value;
  }

  public void setInternational(boolean value) {
    this.international = value;
  }

  public void setDailyLimit(BigDecimal value) {
    this.dailyLimit = positiveOrNull(value, "dailyLimit");
  }

  public void setPerTransactionLimit(BigDecimal value) {
    this.perTransactionLimit = positiveOrNull(value, "perTransactionLimit");
  }

  /** Authorize a spend of {@code amount} on the given channel; throws if a control blocks it. */
  public void authorize(BigDecimal amount, Channel channel) {
    if (amount == null || amount.signum() <= 0) {
      throw new IllegalArgumentException("amount must be positive");
    }
    if (!channelEnabled(channel)) {
      throw new IllegalArgumentException("channel disabled: " + channel);
    }
    if (channel == Channel.INTERNATIONAL && !international) {
      throw new IllegalArgumentException("international spend blocked");
    }
    if (perTransactionLimit != null && amount.compareTo(perTransactionLimit) > 0) {
      throw new IllegalArgumentException("amount exceeds per-transaction limit");
    }
  }

  private boolean channelEnabled(Channel channel) {
    return switch (channel) {
      case ONLINE -> online;
      case CONTACTLESS -> contactless;
      case ATM -> atm;
      case INTERNATIONAL -> international;
    };
  }

  private static BigDecimal positiveOrNull(BigDecimal value, String field) {
    if (value != null && value.signum() <= 0) {
      throw new IllegalArgumentException(field + " must be positive");
    }
    return value;
  }

  public enum Channel {
    ONLINE,
    CONTACTLESS,
    ATM,
    INTERNATIONAL
  }
}