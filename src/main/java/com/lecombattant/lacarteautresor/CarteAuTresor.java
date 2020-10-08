package com.lecombattant.lacarteautresor;

import com.lecombattant.lacarteautresor.domain.Carte;
import com.lecombattant.lacarteautresor.service.CarteAuTresorService;
import com.lecombattant.lacarteautresor.service.impl.CarteAuTresorServiceImpl;

/** La carte au tresor */
public class CarteAuTresor {
  public static void main(String[] args) {
    CarteAuTresorService carteAuTresorService = CarteAuTresorServiceImpl.builder().build();

    if (args == null || args.length < 2 | args.length > 2) {
      System.out.print("Parametres manquants/incorrects :\n");
      System.out.print("Veillez appelez la jar de la facon suivante  :\n");
      System.out.print("\tjava -jar CarteAuTresor.jar <fichier_entree> <fichier_sortie>\n");
      System.out.print(
          "\t<fichier_entree> : fichier d'initialisation de la carte, veillez renseignez le chemin ABSOLU\n");
      System.out.print(
          "\t<fichier_sortie> : fichier de sauvegarde de la carte, veillez renseignez le chemin ABSOLU\n");
    } else {
      Carte carte = carteAuTresorService.initialiseCarte(args[0]);
      carte.getAventuriers()
          .forEach(carteAuTresorService::executeMovementAventurier);
      carteAuTresorService.enregistrerCarte(carte, args[1]);
    }
  }
}
