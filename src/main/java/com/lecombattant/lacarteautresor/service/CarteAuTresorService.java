package com.lecombattant.lacarteautresor.service;

import com.lecombattant.lacarteautresor.domain.Aventurier;
import com.lecombattant.lacarteautresor.domain.Carte;

public interface CarteAuTresorService {
  Carte initialiseCarte(String fichierInitCarte);

  Carte executeMovementAventurier(Aventurier aventurier);

  void enregistrerCarte(Carte carte, String cheminSauvegarde);

  String afficherCarte(Carte carte);
}
