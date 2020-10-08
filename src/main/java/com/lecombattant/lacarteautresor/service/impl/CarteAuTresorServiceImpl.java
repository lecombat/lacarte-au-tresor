package com.lecombattant.lacarteautresor.service.impl;

import com.lecombattant.lacarteautresor.domain.Aventurier;
import com.lecombattant.lacarteautresor.domain.Carte;
import com.lecombattant.lacarteautresor.service.CarteAuTresorService;
import com.lecombattant.lacarteautresor.utils.Utils;
import lombok.Builder;

import java.util.Arrays;

@Builder
public class CarteAuTresorServiceImpl implements CarteAuTresorService {

  public Carte initialiseCarte(String cheminAbsoluVersfichier) {
    return Utils.lireFichierCarte(cheminAbsoluVersfichier);
  }

  /**
   * Effectue un deplacement unitaire d'un aventurier si tout les deplacements sont déja effectués
   * alors on log un message de fin
   *
   * @param aventurier
   * @return
   */
  public Carte executeMovementAventurier(Aventurier aventurier) {
    String deplacement = aventurier.getProchainDeplacement();
    System.out.println("###########DEBUT#################");
    System.out.println(afficherCarte(aventurier.getCarte()));
    System.out.println("###################################");
    while (deplacement != null) {
      Utils.deplacement(aventurier, deplacement);
      System.out.println(afficherCarte(aventurier.getCarte()));
      deplacement = aventurier.getProchainDeplacement();
    }
    System.out.println("###########FIN#################");
    System.out.println(afficherCarte(aventurier.getCarte()));
    System.out.println("###################################");
    System.out.println(
            "Tout les deplacements de l'aventurier " + aventurier.getNomAventurier() + " sont fait !!!!!!!!!!!");
    return aventurier.getCarte();
  }

  /**
   * Enregistre l'etat de la carte dans le fichier passé en parametre
   * @param carte
   * @param cheminSauvegarde
   */
  public void enregistrerCarte(Carte carte, String cheminSauvegarde) {
    Utils.enregistreFichierCarte(carte, cheminSauvegarde);
  }


  public String afficherCarte(Carte carte) {
    StringBuilder result = new StringBuilder();
    Arrays.stream(carte.getCarte()).forEach(ligne -> {
      Arrays.stream(ligne).forEach(c -> result.append(c.getCode()).append("     "));
      result.append("\n");
    });

    return result.toString();
  }
}
