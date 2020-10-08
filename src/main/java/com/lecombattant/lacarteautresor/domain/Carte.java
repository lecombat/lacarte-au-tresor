package com.lecombattant.lacarteautresor.domain;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
public class Carte {
  private int longueurAxeVerticale;
  private int longueurAxeHorizontale;
  private Case[][] carte;

  // Liste des aventuriers à deplacer
  private List<Aventurier> aventuriers;

  /**
   * Initialise une carte de dimension largeur*longueur Avec des Plaine
   *
   * @param longueurAxeVerticale
   * @param longueurAxeHorizontale
   * @return
   */
  public static Carte initCarte(int longueurAxeHorizontale, int longueurAxeVerticale) {
    Case[][] carte = new Case[longueurAxeVerticale][longueurAxeHorizontale];
    Arrays.stream(carte).forEach(ligne -> Arrays.fill(ligne, Plaine.PlaineBuilder().build()));
    return Carte.builder()
        .longueurAxeVerticale(longueurAxeVerticale)
        .longueurAxeHorizontale(longueurAxeHorizontale)
        .carte(carte)
        .aventuriers(new ArrayList<Aventurier>())
        .build();
  }

  /**
   * Retourne la case situé à la position (axeH, axeV) de la carte
   *
   * @param axeHorizontale
   * @param axeVerticale
   * @return
   */
  public Case getCase(int axeHorizontale, int axeVerticale) {
    return carte[axeVerticale][axeHorizontale];
  }

  /**
   * Set la case passé en parametre à la postion (axeH, axeV) de la carte
   *
   * @param axeHorizontale
   * @param axeVerticale
   * @param c
   */
  public void setCase(int axeHorizontale, int axeVerticale, Case c) {
    carte[axeVerticale][axeHorizontale] = c;
  }
}
