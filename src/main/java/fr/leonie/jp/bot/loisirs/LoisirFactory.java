package fr.leonie.jp.bot.loisirs;

public class LoisirFactory {
    private LoisirFactory() {}

    public static Loisir getLoisir(String nom, int nbParticipants, String theme) {
        Loisir loisir = null;
        try {
            switch (theme) {
                case "sport":
                    loisir = new Sport(nom, nbParticipants);
                    break;
                case "jeu":
                    loisir = new Jeu(nom, nbParticipants);
                    break;
            }
        } catch (IllegalArgumentException e) {
            //System.out.println(e.getMessage());
        }
        return loisir;
    }
}
