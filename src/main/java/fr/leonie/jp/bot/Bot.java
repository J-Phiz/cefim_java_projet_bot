package fr.leonie.jp.bot;

import fr.leonie.jp.bot.communication.Communication;
import fr.leonie.jp.bot.utilisateurs.Utilisateur;
import fr.leonie.jp.bot.utilisateurs.UtilisateurFactory;
import fr.leonie.jp.bot.xml.ExportXML;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Bot {
    private static final Bot INSTANCE = new Bot();
    private final String nom;
    private final ArrayList<Utilisateur> listeUtilisateurs;

    private final String[] yesAnswersArray = {"oui", "yes", "yep", "ouai", "ouep"};
    private final String[] noAnswersArray = {"non", "no", "nop", "nan"};
    private final List<String> yesAnswers = Arrays.asList(yesAnswersArray);
    private final List<String> noAnswers = Arrays.asList(noAnswersArray);

    private Bot() {
        nom = "MeetBot";
        listeUtilisateurs = new ArrayList<Utilisateur>();
    }

    public static Bot getInstance() {
        return INSTANCE;
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<Utilisateur> getListeUtilisateurs() {
        return listeUtilisateurs;
    }

    public void discuss(Communication com) {
        String prenom = null;
        String nomF = null;

        com.send("Bonjour, je suis MeetBot. Comment t'appelles-tu ?");
        String response = com.receive();
        String[] splitResponse = response.split(" ");
        if(response.split(" ").length == 2){
            prenom = splitResponse[0];
            nomF = splitResponse[1];
        } else if(response.split(" ").length == 1) {
            com.send("C'est ton prénom ?");
            response = com.receive();
            if(yesAnswers.contains(response)) {
                prenom = splitResponse[0];
                com.send("Et quel est ton nom ?");
                response = com.receive();
                nomF = response;
            } else if (noAnswers.contains(response)) {
                com.send("Alors c'est ton nom ?");
                response = com.receive();
                if(yesAnswers.contains(response)) {
                    nomF = splitResponse[0];
                    com.send("Et quel est ton prénom ?");
                    response = com.receive();
                    prenom = response;
                } else {
                    com.send("Tu te moques de moi !!");
                }
            } else {
                com.send("C'est une question simple, peux-tu répondre par oui ou par non...");
            }
        } else {
            com.send("C'est un nom ça ?!?");
        }

        com.send("Quel âge as-tu ?");
        response = com.receive();
        int age = Integer.parseInt(response);

        com.send("Tu te décris comme étant plutôt : \n"
        + "- fou de sport : tape 1\n"
        + "- accro à la culture : tape 2\n"
        + "- passionné de jeux : tape 3");
        response = com.receive();
        String theme = "";
        switch(response) {
            case "1" :
                theme = "sport";
                break;
            case "2":
                theme = "culture";
                break;
            case "3":
                theme = "jeux";
                break;
            default:
                com.send("Tu as bien lu la question ?");
                break;
        }

        Utilisateur utilisateur = UtilisateurFactory.getUtilisateur(nomF, prenom, age, "Lille", theme);
        listeUtilisateurs.add(utilisateur);
        // exportXML
        ExportXML.exportUtilisateurs(listeUtilisateurs);

        if(prenom != null && nomF != null) {
            com.send("Au revoir " + prenom + " " + nomF);
        } else {
            com.send("Bye");
        }
    }

}
