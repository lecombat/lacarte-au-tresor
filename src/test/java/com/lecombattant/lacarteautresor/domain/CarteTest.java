package com.lecombattant.lacarteautresor.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CarteTest{
    @Test
    @DisplayName("Quand la longueuer et la largeur sont superieur à zero alors retourne une carte de Plaine")
    public void testInitCarte() {
        int largeur = 3;
        int longueur = 4;
        Carte carte = Carte.initCarte(largeur, longueur);
        Assertions.assertNotNull(carte);
        Assertions.assertTrue (carte.getCase(0, 0) instanceof  Plaine);
    }
}