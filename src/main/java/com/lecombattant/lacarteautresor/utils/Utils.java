package com.lecombattant.lacarteautresor.utils;

import com.lecombattant.lacarteautresor.domain.*;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

import static com.lecombattant.lacarteautresor.constantes.Constantes.*;

public class Utils {
  /**
   * Lire le fichier du jeu
   *
   * @param cheminAbsoluVersfichier
   * @return
   */
  public static Carte lireFichierCarte(String cheminAbsoluVersfichier) {
    List<Aventurier> aventuriers = new ArrayList<>();
    List<Montagne> montagnes = new ArrayList<>();
    List<Tresor> tresors = new ArrayList<>();
    int largeur = 0;
    int longueur = 0;

    try (Scanner scanner = new Scanner(new File(cheminAbsoluVersfichier))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        Matcher match;
        if ((match = PATTERN_AVENTURIER.matcher(line)).find()) {
          Aventurier aventurier = Aventurier.initAventurier(match);
          aventuriers.add(aventurier);
        } else if ((match = PATTERN_CARTE.matcher(line)).find()) {
          largeur = Integer.parseInt(match.group(2));
          longueur = Integer.parseInt(match.group(3));
        } else if ((match = PATTERN_MONTAGNE.matcher(line)).find()) {
          Montagne montagne = Montagne.initMontagne(match);
          montagnes.add(montagne);
        } else if ((match = PATTERN_TRESOR.matcher(line)).find()) {
          Tresor tresor = Tresor.initTresor(match);
          tresors.add(tresor);
        } else if (PATTERN_COMMENTAIRE.matcher(line).find()) {
          System.out.println("On ignore les commentaires (#)");
        } else {
          System.out.println("Erreur lors de la lecture du fichier " + cheminAbsoluVersfichier);
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println(
          "Chemin vers le fichier " + cheminAbsoluVersfichier + " incorrect ou inexistant");
    }
    // Init de la carte
    Carte carte = Carte.initCarte(largeur, longueur);
    return remplissageCarte(carte, aventuriers, montagnes, tresors);
  }

  /**
   * Enregistré la carte dans un fichier
   *
   * @param carte
   */
  public static void enregistreFichierCarte(Carte carte, String cheminSauvegarde) {
    List<Case> aventuriers = new ArrayList<>();
    List<Case> montagnes = new ArrayList<>();
    List<Case> tresors = new ArrayList<>();
    int largeur = carte.getLongueurAxeHorizontale();
    int longueur = carte.getLongueurAxeVerticale();

    // Compter chaque type de case
    Arrays.stream(carte.getCarte())
        .forEach(
            ligne -> Arrays.stream(ligne)
                .forEach(
                    c -> {
                      if (c.getCode().contains(CODE_MONTAGNE)) {
                        montagnes.add(c);
                      } else if (c.getCode().contains(CODE_AVENTURIER)) {
                        aventuriers.add(c);
                      } else if (c.getCode().contains(CODE_TRESOR)) {
                        tresors.add(c);
                      }
                    }));

    try (FileWriter fileWriter = new FileWriter(cheminSauvegarde)) {
      PrintWriter printWriter = new PrintWriter(fileWriter);
      printWriter.printf("C - %d - %d\n", largeur, longueur);
      montagnes
          .forEach(
              c -> printWriter.printf("M - %d - %d\n", c.getAxeHorizontale(), c.getAxeVerticale()));

      printWriter.printf(
          "# {T comme Trésor} - {Axe horizontal} - {Axe vertical} - {Nb. de trésors restants}\n");
      tresors
          .forEach(
              c -> printWriter.printf(
                  "T - %d - %d - %d\n",
                  c.getAxeHorizontale(), c.getAxeVerticale(), ((Tresor) c).getNombre()));

      printWriter.printf(
          "# {A comme Aventurier} - {Nom de l’aventurier} - {Axe horizontal} - {Axe\n"
              + "vertical} - {Orientation} - {Nb. trésors ramassés}\n");
      aventuriers
          .forEach(
              c -> printWriter.printf(
                  "A - %s - %d - %d - %S - %d\n",
                  ((Aventurier) c).getNomAventurier(),
                  c.getAxeHorizontale(),
                  c.getAxeVerticale(),
                  ((Aventurier) c).getOrientation(),
                  ((Aventurier) c).getNombreTresorCollectes()));
    } catch (IOException e) {
      System.out.printf(
          "Erreur lors de la creation du fichier %s de sauvegarde de la carte \n", cheminSauvegarde);
    }
  }

  /**
   * Rempli la carte avec les A, T, M
   *
   * @param carte
   * @param aventuriers
   * @param montagnes
   * @param tresors
   * @return
   */
  private static Carte remplissageCarte(
      Carte carte, List<Aventurier> aventuriers, List<Montagne> montagnes, List<Tresor> tresors) {
    // Remplissage aventurier
    aventuriers.forEach(
        a -> {
          carte.setCase(a.getAxeHorizontale(), a.getAxeVerticale(), a);
          carte.getAventuriers().add(a);
          a.setCarte(carte);
        });
    // Remplissage de la montagne
    montagnes.forEach(m -> carte.setCase(m.getAxeHorizontale(), m.getAxeVerticale(), m));
    // Remplissage des tresors
    tresors.forEach(t -> carte.setCase(t.getAxeHorizontale(), t.getAxeVerticale(), t));
    return carte;
  }

  /**
   * @param aventurier
   * @param prochainDeplacement
   */
  public static void deplacement(Aventurier aventurier, String prochainDeplacement) {
    String orientation = aventurier.getOrientation();
    int axeH = aventurier.getAxeHorizontale();
    int axeV = aventurier.getAxeVerticale();
    if (ORIENTATION_S.equals(orientation)) {
      deplacementOrientationSud(aventurier, prochainDeplacement, axeH, axeV);
    } else if (ORIENTATION_N.equals(orientation)) {
      deplacementOrientationNord(aventurier, prochainDeplacement, axeH, axeV);
    } else if (ORIENTATION_O.equals(orientation)) {
      deplacementOrientationOuest(aventurier, prochainDeplacement, axeH, axeV);
    } else if (ORIENTATION_E.equals(orientation)) {
      deplacementOrientationEst(aventurier, prochainDeplacement, axeH, axeV);
    }
  }

  /**
   * @param aventurier
   * @param prochainDeplacement
   * @param axeH
   * @param axeV
   */
  private static void deplacementOrientationSud(
      Aventurier aventurier, String prochainDeplacement, int axeH, int axeV) {
    Carte carte = aventurier.getCarte();
    int longueur = carte.getLongueurAxeVerticale();

    if (MOUVEMENT_AVANCER.equals(prochainDeplacement)) {
      if (axeV + 1 < longueur
          && !carte.getCase(axeH, axeV + 1).getCode().equals(CODE_MONTAGNE)
          && !carte.getCase(axeH, axeV + 1).getCode().contains(CODE_AVENTURIER)) {
        Case caseSuivante = carte.getCase(axeH, axeV + 1);
        miseAjourNombreTresor(aventurier, caseSuivante);
        loggingDeplacementAventurier(aventurier, caseSuivante, ORIENTATION_SUD);
        // Mise à jour de la carte
        carte.setCase(axeH, axeV, Plaine.PlaineBuilder().code(CODE_PLAINE).build());
        aventurier.setAxeHorizontale(axeH);
        aventurier.setAxeVerticale(axeV + 1);
        carte.setCase(axeH, axeV + 1, aventurier);
      } else {
        System.out.printf(
            "Mouvement impossible, le deplacement vers le %s sera ignoré\n\n", ORIENTATION_SUD);
      }
    } else if (MOUVEMENT_TOURNER_A_DROITE.equals(prochainDeplacement)) {
      aventurier.setOrientation(ORIENTATION_O);
    } else if (MOUVEMENT_TOURNER_A_GAUCHE.equals(prochainDeplacement)) {
      aventurier.setOrientation(ORIENTATION_E);
    }
  }

  /**
   * @param aventurier
   * @param caseSuivante
   */
  private static void miseAjourNombreTresor(Aventurier aventurier, Case caseSuivante) {
    if (caseSuivante.getCode().contains(CODE_TRESOR)) {
      Tresor tresor = ((Tresor) caseSuivante);
      if (tresor.getNombre() > 0) {
        tresor.decrementerNombreTresor();
        aventurier.incrementerNombreTresorCollectes();
      }
    }
  }

  /**
   * @param aventurier
   * @param caseSuivante
   * @param orientation
   */
  private static void loggingDeplacementAventurier(
      Aventurier aventurier, Case caseSuivante, String orientation) {
    System.out.printf(
        "Deplacement de l'aventurier %s de la position %s vers le %s\n\n",
        aventurier.getCode(), aventurier.getCoordonnees(), orientation);
  }

  /**
   * @param aventurier
   * @param prochainDeplacement
   * @param axeH
   * @param axeV
   */
  private static void deplacementOrientationNord(
      Aventurier aventurier, String prochainDeplacement, int axeH, int axeV) {
    Carte carte = aventurier.getCarte();

    if (MOUVEMENT_AVANCER.equals(prochainDeplacement)) {
      if (axeV - 1 >= 0
          && !carte.getCase(axeH, axeV - 1).getCode().equals(CODE_MONTAGNE)
          && !carte.getCase(axeH, axeV - 1).getCode().equals(CODE_AVENTURIER)) {
        Case caseSuivante = carte.getCase(axeH, axeV - 1);
        miseAjourNombreTresor(aventurier, caseSuivante);
        loggingDeplacementAventurier(aventurier, caseSuivante, ORIENTATION_NORD);
        // Mise à jour de la carte
        carte.setCase(axeH, axeV, Plaine.PlaineBuilder().code(CODE_PLAINE).build());
        aventurier.setAxeHorizontale(axeH);
        aventurier.setAxeVerticale(axeV - 1);
        carte.setCase(axeH, axeV - 1, aventurier);
      } else {
        System.out.printf(
            "Mouvement impossible, le deplacement vers le %s sera ignoré\n\n", ORIENTATION_NORD);
      }
    } else if (MOUVEMENT_TOURNER_A_DROITE.equals(prochainDeplacement)) {
      aventurier.setOrientation(ORIENTATION_E);
    } else if (MOUVEMENT_TOURNER_A_GAUCHE.equals(prochainDeplacement)) {
      aventurier.setOrientation(ORIENTATION_O);
    }
  }

  /**
   * @param aventurier
   * @param prochainDeplacement
   * @param axeH
   * @param axeV
   */
  private static void deplacementOrientationOuest(
      Aventurier aventurier, String prochainDeplacement, int axeH, int axeV) {
    Carte carte = aventurier.getCarte();

    if (MOUVEMENT_AVANCER.equals(prochainDeplacement)) {
      if (axeH - 1 >= 0
          && !carte.getCase(axeH - 1, axeV).getCode().equals(CODE_MONTAGNE)
          && !carte.getCase(axeH - 1, axeV).getCode().equals(CODE_AVENTURIER)) {
        Case caseSuivante = carte.getCase(axeH - 1, axeV);
        miseAjourNombreTresor(aventurier, caseSuivante);
        loggingDeplacementAventurier(aventurier, caseSuivante, ORIENTATION_OUEST);
        // Mise à jour de la carte
        carte.setCase(axeH, axeV, Plaine.PlaineBuilder().code(CODE_PLAINE).build());
        aventurier.setAxeHorizontale(axeH - 1);
        aventurier.setAxeVerticale(axeV);
        carte.setCase(axeH - 1, axeV, aventurier);
      } else {
        System.out.printf(
            "Mouvement impossible, le deplacement vers le %s sera ignoré\n\n", ORIENTATION_OUEST);
      }
    } else if (MOUVEMENT_TOURNER_A_DROITE.equals(prochainDeplacement)) {
      aventurier.setOrientation(ORIENTATION_N);
    } else if (MOUVEMENT_TOURNER_A_GAUCHE.equals(prochainDeplacement)) {
      aventurier.setOrientation(ORIENTATION_S);
    }
  }

  /**
   * @param aventurier
   * @param prochainDeplacement
   * @param axeH
   * @param axeV
   */
  private static void deplacementOrientationEst(
      Aventurier aventurier, String prochainDeplacement, int axeH, int axeV) {
    Carte carte = aventurier.getCarte();
    int largeur = carte.getLongueurAxeHorizontale();

    if (MOUVEMENT_AVANCER.equals(prochainDeplacement)) {
      if (axeH + 1 < largeur
          && !carte.getCase(axeH + 1, axeV).getCode().equals(CODE_MONTAGNE)
          && !carte.getCase(axeH + 1, axeV).getCode().equals(CODE_MONTAGNE)) {
        Case caseSuivante = carte.getCase(axeH + 1, axeV);
        miseAjourNombreTresor(aventurier, caseSuivante);
        loggingDeplacementAventurier(aventurier, caseSuivante, ORIENTATION_EST);
        // Mise à jour de la carte
        carte.setCase(axeH, axeV, Plaine.PlaineBuilder().code(CODE_PLAINE).build());
        aventurier.setAxeHorizontale(axeH + 1);
        aventurier.setAxeVerticale(axeV);
        carte.setCase(axeH + 1, axeV, aventurier);
      } else {
        System.out.printf(
            "Mouvement impossible, le deplacement vers le %s sera ignoré\n\n", ORIENTATION_EST);
      }
    } else if (MOUVEMENT_TOURNER_A_DROITE.equals(prochainDeplacement)) {
      aventurier.setOrientation(ORIENTATION_S);
    } else if (MOUVEMENT_TOURNER_A_GAUCHE.equals(prochainDeplacement)) {
      aventurier.setOrientation(ORIENTATION_N);
    }
  }
}
