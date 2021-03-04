package fr.leonie.jp.bot.loisirs;

public class LoisirFactory {
    private LoisirFactory() {}

    public static <T extends Loisir> T getLoisir(String nom, int nbParticipants, String theme) {
        T loisir = null;
        try {
            switch (theme) {
                case "sport":
                    loisir = (T) new Sport(nom, nbParticipants);
                    break;
                case "jeu":
                    loisir = (T) new Jeu(nom, nbParticipants);
                    break;
            }
        } catch (IllegalArgumentException e) {
            //System.out.println(e.getMessage());
        }
        return loisir;
    }
}
