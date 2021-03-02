package fr.leonie.jp.bot.utilisateurs;

import java.util.ArrayList;

public class GrosseTete extends Utilisateur {
    private ArrayList<String> Livres;
    private ArrayList<String> Films;
    private ArrayList<String> Musees;

    public GrosseTete(String pNom, String pPrenom, int pAge, String pVille) {
        super(pNom, pPrenom, pAge, pVille);
    }

    public ArrayList<String> getLivres() {
        return Livres;
    }

    public void setLivres(ArrayList<String> livres) {
        Livres = livres;
    }

    public ArrayList<String> getFilms() {
        return Films;
    }

    public void setFilms(ArrayList<String> films) {
        Films = films;
    }

    public ArrayList<String> getMusees() {
        return Musees;
    }

    public void setMusees(ArrayList<String> musees) {
        Musees = musees;
    }
}
