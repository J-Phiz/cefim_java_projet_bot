package fr.leonie.jp.bot.loisirs;

public class Loisir {
    private final String name;
    private final int nbParticipants;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loisir loisir = (Loisir) o;
        return name.equals(loisir.name);
    }
}
