package com.lecombattant.lacarteautresor.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Plaine extends Case {

  @Builder(builderMethodName = "PlaineBuilder")
  public Plaine(int axeHorizontale, int axeVerticale, String code) {
    super(axeHorizontale, axeVerticale, code);
  }

  @Override
  public String getCode() {
    return ".";
  }
}
