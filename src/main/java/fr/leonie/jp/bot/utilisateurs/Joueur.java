package fr.leonie.jp.bot.utilisateurs;

import fr.leonie.jp.bot.loisirs.Jeu;
import fr.leonie.jp.bot.loisirs.Loisir;

import java.util.ArrayList;

public class Joueur extends Utilisateur {
    private ArrayList<Loisir> jeux;

    public Joueur(String pNom, String pPrenom, int pAge, String pVille) {
        super(pNom, pPrenom, pAge, pVille);
        jeux = new ArrayList<Loisir>();
    }

    @Override
    public ArrayList<Loisir> getListeLoisirs() {
        return jeux;
    }

    @Override
    public String getLoisirCategory() {
        return Jeu.getCategory();
    }
}
