package fr.leonie.jp.bot.loisirs;

public class Sport extends Loisir {
    private static final String CATEGORY = "Sport";

    public Sport(String pName, int pNbJoueurs) {
        super(pName, pNbJoueurs);
    }

    public static String getCategory() {
        return CATEGORY;
    }
}
