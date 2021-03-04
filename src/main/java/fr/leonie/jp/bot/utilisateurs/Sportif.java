package fr.leonie.jp.bot.utilisateurs;

import fr.leonie.jp.bot.loisirs.Loisir;
import fr.leonie.jp.bot.loisirs.Sport;

import java.util.ArrayList;

public class Sportif extends Utilisateur {
    private ArrayList<Loisir> sports;

    public Sportif(String pNom, String pPrenom, int pAge, String pVille) {
        super(pNom, pPrenom, pAge, pVille);
        sports = new ArrayList<Loisir>();
    }

    @Override
    public ArrayList<Loisir> getListeLoisirs() {
        return sports;
    }

    @Override
    public String getLoisirCategory() {
        return Sport.getCategory();
    }
}
