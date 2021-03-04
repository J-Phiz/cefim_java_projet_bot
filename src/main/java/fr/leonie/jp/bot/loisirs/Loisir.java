package fr.leonie.jp.bot.loisirs;

public class Loisir {
    private final String name;
    private final int nbParticipants;
    private String theme;

    public Loisir(String pName, int pNbParticipants) {
        name = pName;
        nbParticipants = pNbParticipants;
    }

    public String getName() {
        return name;
    }

    public int getNbParticipants() {
        return nbParticipants;
    }
}
