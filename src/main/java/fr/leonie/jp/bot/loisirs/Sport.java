package fr.leonie.jp.bot.loisirs;

public class Sport {
    private final String nom;
    private final String type;

    public Sport(String nom, String type) {
        this.nom = nom;
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }
}
