package com.lecombattant.lacarteautresor.service.impl;

import com.lecombattant.lacarteautresor.domain.Aventurier;
import com.lecombattant.lacarteautresor.domain.Carte;
import com.lecombattant.lacarteautresor.domain.Montagne;
import com.lecombattant.lacarteautresor.domain.Tresor;
import com.lecombattant.lacarteautresor.service.CarteAuTresorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;

public class CarteAuTresorServiceImplTest {
  CarteAuTresorService cut = CarteAuTresorServiceImpl.builder().build();

  @Test
  @DisplayName("Quand le chemin du fichier est present, retourne la carte correspondante")
  public void testInitialiseCarte() {
    String cheminFichier = "src/test/resources/initCarte.txt";
    Carte carte = cut.initialiseCarte(cheminFichier);

    Assertions.assertNotNull(carte);
  }

  @Test
  @DisplayName(
      "Quand le chemin du fichier n'est pas present retourne une carte de taille zero avec un log d'erreur")
  public void testInitialiseCarteFileNotExist() {
    String cheminFichier = "src/test/resources/fileNotExist.txt";
    Carte carte = cut.initialiseCarte(cheminFichier);

    Assertions.assertNotNull(carte);
    Assertions.assertEquals(0, carte.getLongueurAxeVerticale());
    Assertions.assertEquals(0, carte.getLongueurAxeHorizontale());
  }

  @Test
  @DisplayName(
      "Quand le chemin du fichier est present, retourne une carte rempli avec le bon nombre de A, M, P et T")
  public void testInitialiseCarte3Fois4() {
    String cheminFichier = "src/test/resources/initCarte.txt";
    Carte carte = cut.initialiseCarte(cheminFichier);
    String[] values = {"A", "A", "D", "A", "D", "A", "G", "G", "A"} ;
    LinkedList<String> listeDeplacements = new LinkedList<>(Arrays.asList(values));

    Assertions.assertAll(
        () -> Assertions.assertNotNull(carte),
        () -> Assertions.assertEquals(4, carte.getLongueurAxeVerticale()),
        () -> Assertions.assertEquals(3, carte.getLongueurAxeHorizontale()),
        () -> Assertions.assertTrue(carte.getCase(1, 0) instanceof Montagne),
        () -> Assertions.assertTrue(carte.getCase(2, 1) instanceof Montagne),
        () -> Assertions.assertTrue(carte.getCase(0, 3) instanceof Tresor),
        () -> Assertions.assertTrue(carte.getCase(1, 3) instanceof Tresor),
        () -> Assertions.assertTrue(carte.getCase(1, 1) instanceof Aventurier),
        () -> Assertions.assertEquals("S", ((Aventurier) carte.getCase(1, 1)).getOrientation()),
        () ->
                Assertions.assertTrue(
                        ((Aventurier) carte.getCase(1, 1)).getDeplacements().containsAll(listeDeplacements)),
        () -> Assertions.assertEquals(2, ((Tresor) carte.getCase(0, 3)).getNombre()),
        () -> Assertions.assertEquals(3, ((Tresor) carte.getCase(1, 3)).getNombre()));
  }

  @Test
  @DisplayName("Test execution des mouvement d'un aventurrier")
  public void testExecuteMovementAventurier() {
    String cheminFichier = "src/test/resources/initCarte.txt";
    Carte carte = cut.initialiseCarte(cheminFichier);
    String[] values = {"A", "A", "D", "A", "D", "A", "G", "G", "A"} ;
    LinkedList<String> listeDeplacements = new LinkedList<>(Arrays.asList(values));

    Assertions.assertAll(
            () -> Assertions.assertNotNull(carte),
            () -> Assertions.assertEquals(4, carte.getLongueurAxeVerticale()),
            () -> Assertions.assertEquals(3, carte.getLongueurAxeHorizontale()),
            () -> Assertions.assertTrue(carte.getCase(1, 0) instanceof Montagne),
            () -> Assertions.assertTrue(carte.getCase(2, 1) instanceof Montagne),
            () -> Assertions.assertTrue(carte.getCase(0, 3) instanceof Tresor),
            () -> Assertions.assertTrue(carte.getCase(1, 3) instanceof Tresor),
            () -> Assertions.assertTrue(carte.getCase(1, 1) instanceof Aventurier),
            () -> Assertions.assertEquals("S", ((Aventurier) carte.getCase(1, 1)).getOrientation()),
            () ->
                    Assertions.assertTrue(
                            ((Aventurier) carte.getCase(1, 1)).getDeplacements().containsAll(listeDeplacements)),
            () -> Assertions.assertEquals(2, ((Tresor) carte.getCase(0, 3)).getNombre()),
            () -> Assertions.assertEquals(3, ((Tresor) carte.getCase(1, 3)).getNombre()));


    Assertions.assertNotNull(cut.executeMovementAventurier((Aventurier) carte.getCase(1, 1)));
  }

  @Test
  @DisplayName("Quand la carte est non null, enregistre l'etat de la carte dans un fichier")
  public void testEnregistrerCarte() {
    String cheminFichier = "src/test/resources/initCarte.txt";
    Carte carte = cut.initialiseCarte(cheminFichier);
    String[] values = {"A", "A", "D", "A", "D", "A", "G", "G", "A"} ;
    LinkedList<String> listeDeplacements = new LinkedList<>(Arrays.asList(values));

    Assertions.assertAll(
            () -> Assertions.assertNotNull(carte),
            () -> Assertions.assertEquals(4, carte.getLongueurAxeVerticale()),
            () -> Assertions.assertEquals(3, carte.getLongueurAxeHorizontale()),
            () -> Assertions.assertTrue(carte.getCase(1, 0) instanceof Montagne),
            () -> Assertions.assertTrue(carte.getCase(2, 1) instanceof Montagne),
            () -> Assertions.assertTrue(carte.getCase(0, 3) instanceof Tresor),
            () -> Assertions.assertTrue(carte.getCase(1, 3) instanceof Tresor),
            () -> Assertions.assertTrue(carte.getCase(1, 1) instanceof Aventurier),
            () -> Assertions.assertEquals("S", ((Aventurier) carte.getCase(1, 1)).getOrientation()),
            () ->
                    Assertions.assertTrue(
                            ((Aventurier) carte.getCase(1, 1)).getDeplacements().containsAll(listeDeplacements)),
            () -> Assertions.assertEquals(2, ((Tresor) carte.getCase(0, 3)).getNombre()),
            () -> Assertions.assertEquals(3, ((Tresor) carte.getCase(1, 3)).getNombre()));


    Assertions.assertNotNull(cut.executeMovementAventurier((Aventurier) carte.getCase(1, 1)));

    cut.enregistrerCarte(carte, "C:\\Users\\ltsobgni\\Dev\\Others\\lacarte-au-tresor\\carte.txt");
  }

  @Test
  @DisplayName("Quand la carte est non null, enregistre l'etat de la carte dans un fichier")
  public void testEnregistrerCarteAvecTresor() {
    String cheminFichier = "src/test/resources/initCarte.txt";
    Carte carte = cut.initialiseCarte(cheminFichier);
    String[] values = {"A", "A", "D", "A", "D", "A", "G", "G", "A"} ;
    LinkedList<String> listeDeplacements = new LinkedList<>(Arrays.asList(values));

    Assertions.assertAll(
            () -> Assertions.assertNotNull(carte),
            () -> Assertions.assertEquals(4, carte.getLongueurAxeVerticale()),
            () -> Assertions.assertEquals(3, carte.getLongueurAxeHorizontale()),
            () -> Assertions.assertTrue(carte.getCase(1, 0) instanceof Montagne),
            () -> Assertions.assertTrue(carte.getCase(2, 1) instanceof Montagne),
            () -> Assertions.assertTrue(carte.getCase(0, 3) instanceof Tresor),
            () -> Assertions.assertTrue(carte.getCase(1, 3) instanceof Tresor),
            () -> Assertions.assertTrue(carte.getCase(1, 1) instanceof Aventurier),
            () -> Assertions.assertEquals("S", ((Aventurier) carte.getCase(1, 1)).getOrientation()),
            () ->
                    Assertions.assertTrue(
                            ((Aventurier) carte.getCase(1, 1)).getDeplacements().containsAll(listeDeplacements)),
            () -> Assertions.assertEquals(2, ((Tresor) carte.getCase(0, 3)).getNombre()),
            () -> Assertions.assertEquals(3, ((Tresor) carte.getCase(1, 3)).getNombre()));

    cut.enregistrerCarte(carte, "C:\\Users\\ltsobgni\\Dev\\Others\\lacarte-au-tresor\\carteAvecTresor.txt");
  }

  @Test
  @DisplayName(
      "Quand la carte est rempli, imprime le resultat sous forme de tableau dans une chaine de caractères")
  public void testAfficherCarte() {
    String cheminFichier = "src/test/resources/initCarte.txt";
    Carte carte = cut.initialiseCarte(cheminFichier);
    String result = cut.afficherCarte(carte);

    Assertions.assertNotNull(result);
    Assertions.assertTrue(cut.afficherCarte(carte).contains("A(Lara)"));
    Assertions.assertTrue(cut.afficherCarte(carte).contains("M"));
    Assertions.assertTrue(cut.afficherCarte(carte).contains("T(2)"));
    Assertions.assertTrue(cut.afficherCarte(carte).contains("T(3)"));
  }
}
