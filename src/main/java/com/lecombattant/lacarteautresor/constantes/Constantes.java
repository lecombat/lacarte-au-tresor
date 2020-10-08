package com.lecombattant.lacarteautresor.constantes;

import java.util.regex.Pattern;

public class Constantes {

    // Pattern de recuperation d'une ligne aventurier
    public static final Pattern PATTERN_AVENTURIER = Pattern.compile("\\s*(A)\\s*-\\s*(\\w+)\\s*-\\s*(\\d)\\s*-\\s*(\\d)\\s*-\\s*([NSEO])\\s*-\\s*([AGD]*)");
    // Pattern de recuperation de la ligne carte
    public static final Pattern PATTERN_CARTE = Pattern.compile("\\s*(C)\\s*-\\s*(\\d)\\s*-\\s*(\\d)\\s*");
    // Pattern de recuperation d'un ligne montagne
    public static final Pattern PATTERN_MONTAGNE = Pattern.compile("\\s*(M)\\s*-\\s*(\\d)\\s*-\\s*(\\d)\\s*");
    // Pattern de recuperation d'une ligne tresor
    public static final Pattern PATTERN_TRESOR = Pattern.compile("\\s*(T)\\s*-\\s*(\\d)\\s*-\\s*(\\d)\\s*-\\s*(\\d)\\s*");
    // //Pattern de recuperation de la ligne commentaire
    public static final Pattern PATTERN_COMMENTAIRE = Pattern.compile("#.*");

    // Orientation
    public static final String ORIENTATION_S = "S";
    public static final String ORIENTATION_N = "N";
    public static final String ORIENTATION_O = "O";
    public static final String ORIENTATION_E = "E";

    public static final String ORIENTATION_SUD = "SUD";
    public static final String ORIENTATION_NORD = "NORD";
    public static final String ORIENTATION_OUEST = "OUEST";
    public static final String ORIENTATION_EST = "EST";

    // Movement
    public static final String MOUVEMENT_AVANCER = "A";
    public static final String MOUVEMENT_TOURNER_A_DROITE = "D";
    public static final String MOUVEMENT_TOURNER_A_GAUCHE = "G";

    // Code Case
    public static final String CODE_AVENTURIER = "A";
    public static final String CODE_MONTAGNE = "M";
    public static final String CODE_TRESOR = "T";
    public static final String CODE_PLAINE = ".";


}
