package fr.leonie.jp.bot.loisirs;

public class Jeu extends Loisir {
    private static final String CATEGORY = "jeu";

    public Jeu(String pName, int pNbJoueurs) {
        super(pName, pNbJoueurs);
    }

    public static String getCategory() {
        return CATEGORY;
    }
}
