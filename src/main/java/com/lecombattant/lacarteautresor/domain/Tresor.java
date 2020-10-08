package com.lecombattant.lacarteautresor.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.regex.Matcher;

@Getter
@Setter
public class Tresor extends Case {
  private int nombre;

  @Builder(builderMethodName = "TresorBuilder")
  public Tresor(int axeHorizontale, int axeVerticale, int nombreTresor, String code) {
    super(axeHorizontale, axeVerticale, code);
    this.nombre = nombreTresor;
  }

  public static Tresor initTresor(Matcher matchTresor) {
    return Tresor.TresorBuilder()
        .axeHorizontale(Integer.parseInt(matchTresor.group(2)))
        .axeVerticale(Integer.parseInt(matchTresor.group(3)))
        .nombreTresor(Integer.parseInt(matchTresor.group(4)))
        .build();
  }

  @Override
  public String getCode() {
    return "T("+nombre+")";
  }

  public void decrementerNombreTresor(){
    this.nombre--;
  }
}
