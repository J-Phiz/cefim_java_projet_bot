package fr.leonie.jp.bot.utilisateurs;

import java.util.HashMap;

public final class UtilisateurFactory {
    private UtilisateurFactory() {}

    public static Utilisateur getUtilisateur(String pNom, String pPrenom, int pAge, String pVille, String theme) { // theme en ENUM ?
        Utilisateur utilisateur = null;
        try {
            switch(theme) {
                case "sport":
                    utilisateur = new Sportif(pNom, pPrenom, pAge, pVille);
                    break;
                case "jeux":
                    utilisateur = new Joueur(pNom, pPrenom, pAge, pVille);
                    break;
                case "culture":
                    utilisateur = new GrosseTete(pNom, pPrenom, pAge, pVille);
                    break;
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return utilisateur;
    }
}
