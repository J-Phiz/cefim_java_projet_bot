package fr.leonie.jp.bot;

import fr.leonie.jp.bot.utilisateurs.Utilisateur;
import fr.leonie.jp.bot.utilisateurs.UtilisateurFactory;
import fr.leonie.jp.bot.xml.ExportXML;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Bot implements Runnable {
    private static final Bot INSTANCE = new Bot();
    private final String nom;
    private final ArrayList<Utilisateur> listeUtilisateurs;
    private static final Scanner SCANNER = new Scanner(System.in);

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

    private void discuss() {
        String prenom = null;
        String nomF = null;

        System.out.println("Bonjour, je suis MeetBot. Comment t'appelles-tu ?");
        String response = SCANNER.nextLine();
        String[] splitResponse = response.split(" ");
        if(response.split(" ").length == 2){
            prenom = splitResponse[0];
            nomF = splitResponse[1];
        } else if(response.split(" ").length == 1) {
            System.out.println("C'est ton prénom ?");
            response = SCANNER.nextLine();
            if(yesAnswers.contains(response)) {
                prenom = splitResponse[0];
                System.out.println("Et quel est ton nom ?");
                response = SCANNER.nextLine();
                nomF = response;
            } else if (noAnswers.contains(response)) {
                System.out.println("Alors c'est ton nom ?");
                response = SCANNER.nextLine();
                if(yesAnswers.contains(response)) {
                    nomF = splitResponse[0];
                    System.out.println("Et quel est ton prénom ?");
                    response = SCANNER.nextLine();
                    prenom = response;
                } else {
                    System.out.println("Tu te moques de moi !!");
                }
            } else {
                System.out.println("C'est une question simple, peux-tu répondre par oui ou par non...");
            }
        } else {
            System.out.println("C'est un nom ça ?!?");
        }

        System.out.println("Quel âge as-tu ?");
        response = SCANNER.nextLine();
        int age = Integer.parseInt(response);

        System.out.println("Tu te décris comme étant plutôt : ");
        System.out.println("- fou de sport : tape 1");
        System.out.println("- accro à la culture : tape 2");
        System.out.println("- passionné de jeux : tape 3");
        response = SCANNER.nextLine();
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
                System.out.println("Tu as bien lu la question ?");
                break;
        }

        Utilisateur utilisateur = UtilisateurFactory.getUtilisateur(nomF, prenom, age, "Lille", theme);
        listeUtilisateurs.add(utilisateur);
        // exportXML
        ExportXML.exportUtilisateurs(listeUtilisateurs);

        if(prenom != null && nomF != null) {
            System.out.println("Au revoir " + prenom + " " + nomF);
            System.out.println(utilisateur.getClass().getSimpleName());
        } else {
            System.out.println("Bye");
        }
        SCANNER.close();
    }

    @Override
    public void run() {
        this.discuss();
    }
}
