package com.lecombattant.lacarteautresor.domain;

import lombok.Builder;

import java.util.regex.Matcher;

public class Montagne extends Case {

  @Builder(builderMethodName = "MontagneBuilder")
  public Montagne(int axeHorizontale, int axeVerticale, String code) {
    super(axeHorizontale, axeVerticale, code);
  }

  public static Montagne initMontagne(Matcher matchMontagne) {
    return Montagne.MontagneBuilder()
        .axeHorizontale(Integer.parseInt(matchMontagne.group(2)))
        .axeVerticale(Integer.parseInt(matchMontagne.group(3)))
        .code("M")
        .build();
  }
}
