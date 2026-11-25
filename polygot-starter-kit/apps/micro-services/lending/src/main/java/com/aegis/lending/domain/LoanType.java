package com.aegis.lending.domain;

/** Product type of a loan. Products are configuration in the core; this enum is the local set. */
public enum LoanType {
  PERSONAL,
  AUTO,
  MORTGAGE,
  STUDENT,
  OVERDRAFT,
  BNPL,
  CREDIT_LINE,
  SECURED,
  HELOC
}