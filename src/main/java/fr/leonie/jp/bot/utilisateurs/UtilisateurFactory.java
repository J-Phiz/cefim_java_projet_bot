package fr.leonie.jp.bot.utilisateurs;

import java.util.HashMap;

public final class UtilisateurFactory {
    private UtilisateurFactory() {}

    public static Utilisateur getUtilisateur(String pNom, String pPrenom, int pAge, String theme, HashMap<String, String> options) { // theme en ENUM ?


        Utilisateur utilisateur = null;
        try {
            switch(theme) {
                case "sport":
                    Sportif.SportifBuilder utilisateurTemp = new Sportif.SportifBuilder(pNom, pPrenom, pAge);
                    if(options.get("sport") != null) {
                        utilisateurTemp.sport(options.get("sport"));
                    }
                    if(options.get("nbSeancesParSemaine") != null) {
                        utilisateurTemp.nbSeancesParSemaine(Integer.parseInt(options.get("nbSeancesParSemaine")));
                    }
                    if(options.get("niveau") != null) {
                        utilisateurTemp.nbSeancesParSemaine(Integer.parseInt(options.get("niveau")));
                    }
                    utilisateur = utilisateurTemp.build();
                    break;
                case "jeux":
                    break;
                case "culture":
                    break;
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return utilisateur;
    }
}
