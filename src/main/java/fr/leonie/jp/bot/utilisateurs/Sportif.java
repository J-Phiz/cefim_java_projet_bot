package fr.leonie.jp.bot.utilisateurs;

public class Sportif extends Utilisateur {
    // optional
    private final String sport;
    private final int nbSeancesParSemaine;
    private final int niveau; // ENUM

    private Sportif(SportifBuilder sportifBuilder) {
        super(sportifBuilder.nom, sportifBuilder.prenom, sportifBuilder.age);
        sport = sportifBuilder.sport;
        nbSeancesParSemaine = sportifBuilder.nbSeancesParSemaine;
        niveau = sportifBuilder.niveau;
    }

    public String getSport() {
        return sport;
    }

    public int getNbSeancesParSemaine() {
        return nbSeancesParSemaine;
    }

    public int getNiveau() {
        return niveau;
    }

    public static class SportifBuilder {
        // required
        private final String nom;
        private final String prenom;
        private final int age;

        // optional
        private String sport;
        private int nbSeancesParSemaine;
        private int niveau; // ENUM

        public SportifBuilder(String pNom, String pPrenom, int pAge) {
            nom = pNom;
            prenom = pPrenom;
            age = pAge;
            sport = "non précisé";
            nbSeancesParSemaine = 0;
            niveau = 0;
        }

        public SportifBuilder sport(String pSport) {
            sport = pSport;
            return this;
        }

        public SportifBuilder nbSeancesParSemaine(int pNbSeancesParSemaine) {
            if(pNbSeancesParSemaine >= 0) {
                nbSeancesParSemaine = pNbSeancesParSemaine;
            }
            return this;
        }

        public SportifBuilder niveau(int pNiveau) {
            if(pNiveau >= 0) {
                niveau = pNiveau;
            }
            return this;
        }

        public Sportif build() {
            Sportif sportif =  new Sportif(this);
            return sportif;
        }
    }
}
