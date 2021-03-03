package fr.leonie.jp.bot.utilisateurs;

import fr.leonie.jp.bot.loisirs.Sport;

import java.util.ArrayList;

public class Sportif extends Utilisateur {
    private ArrayList<Sport> sports = new ArrayList<Sport>();
    private int nbSeancesParSemaine;
    private int niveau;

   /* private enum Niveau {
        PRO(4, "PRO"),
        COMPETITION(3, "COMPETITION"),
        AMATEUR(2, "AMATEUR"),
        NOVICE(1, "NOVICE");

        private String name;
        private int value;

        private Niveau(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }
    }*/

    public Sportif(String pNom, String pPrenom, int pAge, String pVille) {
        super(pNom, pPrenom, pAge, pVille);
    }

    public void addSport(Sport sport) {
        this.sports.add(sport);
    }

    public void setNbSeancesParSemaine(int nbSeancesParSemaine) {
        this.nbSeancesParSemaine = nbSeancesParSemaine;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public ArrayList<Sport> getSports() {
        return sports;
    }

    public int getNbSeancesParSemaine() {
        return nbSeancesParSemaine;
    }

    public int getNiveau() {
        return niveau;
    }
}
