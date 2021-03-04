package fr.leonie.jp.bot.utilisateurs;

import fr.leonie.jp.bot.loisirs.Jeu;
import fr.leonie.jp.bot.loisirs.Loisir;
import fr.leonie.jp.bot.loisirs.Sport;

import java.util.ArrayList;

public class Joueur extends Utilisateur {
    private ArrayList<Jeu> jeux;

    public Joueur(String pNom, String pPrenom, int pAge, String pVille) {
        super(pNom, pPrenom, pAge, pVille);
        jeux = new ArrayList<Jeu>();
    }

    @Override
    public ArrayList<Jeu> getListeLoisirs() {
        return jeux;
    }

    @Override
    public String getLoisirCategory() {
        return Jeu.getCategory();
    }
}
