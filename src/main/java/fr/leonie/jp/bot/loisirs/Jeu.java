package fr.leonie.jp.bot.loisirs;

public class Jeu {
    private final String name;
    private final int nbJoueurs;

    public Jeu(String name, int nbJoueurs) {
        this.name = name;
        this.nbJoueurs = nbJoueurs;
    }

    public String getName() {
        return name;
    }

    public int getNbJoueurs() {
        return nbJoueurs;
    }
}
