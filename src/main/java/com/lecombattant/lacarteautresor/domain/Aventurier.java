package com.lecombattant.lacarteautresor.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

@Getter
public class Aventurier extends Case {
  private String nomAventurier;
  @Setter
  private String orientation;
  private LinkedList<String> deplacements;
  @Setter
  private Carte carte;
  private int nombreTresorCollectes;

  @Builder(builderMethodName = "AventurierBuilder")
  public Aventurier(
          int axeHorizontale,
          int axeVerticale,
          String code,
          String nomAventurier,
          String orientation,
          LinkedList<String> deplacements, Carte carte, int nombreTresorCollectes) {
    super(axeHorizontale, axeVerticale, code);
    this.nomAventurier = nomAventurier;
    this.orientation = orientation;
    this.deplacements = deplacements;
    this.carte = carte;
    this.nombreTresorCollectes = nombreTresorCollectes;

  }

  public static Aventurier initAventurier(Matcher matchAventurier) {
    String nomAventurier = matchAventurier.group(2);
    int axeHorizontale = Integer.parseInt(matchAventurier.group(3));
    int axeVerticale = Integer.parseInt(matchAventurier.group(4));
    String orientation = matchAventurier.group(5);
    String[] deplacements = matchAventurier.group(6).split("");

    return Aventurier.AventurierBuilder()
        .nomAventurier(nomAventurier)
        .axeHorizontale(axeHorizontale)
        .axeVerticale(axeVerticale)
        .code("A"+"("+nomAventurier+")")
        .orientation(orientation)
        .deplacements(Arrays.stream(deplacements).collect(Collectors.toCollection(LinkedList::new)))
        .build();
  }

  public String getProchainDeplacement(){
    return this.getDeplacements().isEmpty() ? null : this.getDeplacements().poll();
  }

  public void incrementerNombreTresorCollectes(){
    this.nombreTresorCollectes++;
  }
}
