package fr.leonie.jp.bot.utilisateurs;

import fr.leonie.jp.bot.loisirs.Jeu;

import java.util.ArrayList;

public class Joueur extends Utilisateur {
    private ArrayList<Jeu> jeux;

    public Joueur(String pNom, String pPrenom, int pAge, String pVille) {
        super(pNom, pPrenom, pAge, pVille);
    }

    public void addJeu(Jeu jeu) {
        this.jeux.add(jeu);
    }

    public ArrayList<Jeu> getJeux() {
        return jeux;
    }
}
