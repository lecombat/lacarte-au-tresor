package com.lecombattant.lacarteautresor.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Case {
  private int axeHorizontale;
  private int axeVerticale;
  private String code;

  public String getCoordonnees() {
    return String.format("(%d,%d)", getAxeVerticale(), getAxeHorizontale());
  }
}
