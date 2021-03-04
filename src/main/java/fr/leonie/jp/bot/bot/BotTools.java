package fr.leonie.jp.bot.bot;

import fr.leonie.jp.bot.communication.Communication;
import fr.leonie.jp.bot.constant.Constant;
import fr.leonie.jp.bot.loisirs.Loisir;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BotTools {

    private static final List<String> yesAnswers = Arrays.asList(Constant.getYesAnswersArray());
    private static final List<String> noAnswers = Arrays.asList(Constant.getNoAnswersArray());

    public static int responseOption(Communication com, int nbOptions, String msg) throws IOException {
        String[] msgs = { msg };
        return responseOption(com, nbOptions, msgs);
    }

    public static int responseOption(Communication com, int nbOptions, String[] msgs) throws IOException {
        String response;
        int choice = 0;

        do {
            for (String msg : msgs) {
               com.send(msg);
            }
            response = com.receive();
            try {
                choice = new Integer(response);
                if (choice <= 0 ||
                    (nbOptions > 0 && choice > nbOptions)) {
                    choice = 0;
                    com.send(
                            "J'attends une réponse avec un nombre " +
                                    (nbOptions > 0 ? ("entre 1 et " + nbOptions) : "plus grand que 0") +
                                    " stp..."
                    );
                }
            } catch(NumberFormatException e) {
                com.send("Une réponse avec des chiffres stp...");
            }
        } while (choice == 0);

        return choice;
    }

    public static int responseInt(Communication com, String[] msgs) throws IOException {
        return responseOption(com, 0, msgs);
    }

    public static int responseInt(Communication com, String msg) throws IOException {
        return responseOption(com, 0, msg);
    }

    public static boolean responseYesNo(Communication com, String msg) throws IOException {
        String[] msgs = { msg };
        return responseYesNo(com, msgs);
    }

    public static boolean responseYesNo(Communication com, String[] msgs) throws IOException {
        String response;
        boolean correctFormat = false;
        boolean result = false;

        do {
            for (String msg : msgs) {
                com.send(msg);
            }
            response = com.receive();
            if (noAnswers.contains(response.toLowerCase())) {
                correctFormat = true;
            } else if (yesAnswers.contains(response.toLowerCase())) {
                result = true;
                correctFormat = true;
            } else {
                com.send("C'est une question simple, peux-tu répondre par oui ou par non...");
            }
        } while(!correctFormat);

        return result;
    }

    public static String responseInList(Communication com, ArrayList<Loisir> liste, String msg) throws IOException {
        String[] msgs = { msg };
        return responseInList(com, liste, msgs);
    }

    public static String responseInList(Communication com, ArrayList<Loisir> liste, String[] msgs) throws IOException {
        String response;
        String result = "";
        boolean found = false;

        do {
            for (String msg : msgs) {
                com.send(msg);
            }
            response = com.receive();

            for (Loisir loisir : liste) {
               if (loisir.getName().equals(response)) {
                    found = true;
                    result = response;
                    break;
                }
            }

            if (!found) {
                StringBuilder allowedListe = new StringBuilder();
                com.send("Choisi ton préféré parmi ceux que tu as déjà renseigné :");
                for (Loisir loisir : liste) {
                    allowedListe.append(loisir.getName()).append(" ; ");
                }
                com.send(allowedListe.toString());
            }
        } while(!found);

        return result;
    }
}
