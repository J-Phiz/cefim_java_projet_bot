package fr.leonie.jp.bot;

import fr.leonie.jp.bot.communication.Communication;
import fr.leonie.jp.bot.utilisateurs.*;
import fr.leonie.jp.bot.xml.ExportXML;

import javax.swing.text.html.Option;
import java.util.*;

public class Bot {
    private static final Bot INSTANCE = new Bot();
    private final String nom;
    private final ArrayList<Utilisateur> listeUtilisateurs;

    private final String[] yesAnswersArray = {"oui", "yes", "yep", "ouai", "ouep"};
    private final String[] noAnswersArray = {"non", "no", "nop", "nan"};
    private final List<String> yesAnswers = Arrays.asList(yesAnswersArray);
    private final List<String> noAnswers = Arrays.asList(noAnswersArray);

    private final String[] optionsArray = {"1", "2", "3"};
    private final List<String> options = Arrays.asList(optionsArray);

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
        Optional<String[]> identite;
        do {
            identite = this.validatedName(com);
        } while(identite.isEmpty());

        Optional<Integer> age;
        do {
            age = this.validatedAge(com);
        } while(age.isEmpty());

        Optional<Utilisateur> utilisateur = this.validatedUtilisateur(com, identite, age);

        // exportXML
        ExportXML.exportUtilisateurs(listeUtilisateurs);

        if(utilisateur.isPresent()) {
            com.send("Au revoir " + utilisateur.get().getPrenom() + " " + utilisateur.get().getNom());
        } else {
            com.send("Bye");
        }
    }

    private Optional<String[]> validatedName(Communication com) {
        com.send("Bonjour, je suis MeetBot. Comment t'appelles-tu ?");
        String response = com.receive();

        if(response == null || !response.matches("[a-zA-Z ]+")) {
            com.send("Tu te moques de moi !!");
            return Optional.empty();
        }

        String[] splitResponse = response.split(" ");
        if(splitResponse.length == 2){
            return Optional.of(splitResponse);
        } else if(splitResponse.length == 1) {
            com.send("C'est ton prénom ?");
            response = com.receive();
            if(yesAnswers.contains(response)) {
                String prenom = splitResponse[0];
                com.send("Et quel est ton nom ?");
                response = com.receive();
                if(response.length() == 0 || !response.matches("[a-zA-Z]+")) {
                    com.send("Tu te moques de moi !!");
                    return Optional.empty();
                } else {
                    return Optional.of(new String[]{prenom, response});
                }
            } else if (noAnswers.contains(response)) {
                com.send("Alors c'est ton nom ?");
                response = com.receive();
                if(yesAnswers.contains(response)) {
                    String nomF = splitResponse[0];
                    com.send("Et quel est ton prénom ?");
                    response = com.receive();
                    if(response.length() == 0 || !response.matches("[a-zA-Z]+")) {
                        com.send("Tu te moques de moi !!");
                        return Optional.empty();
                    } else {
                        return Optional.of(new String[]{response, nomF});
                    }
                } else {
                    com.send("Tu te moques de moi !!");
                    return Optional.empty();
                }
            } else {
                com.send("C'est une question simple, peux-tu répondre par oui ou par non...");
                return Optional.empty();
            }
        } else {
            com.send("C'est un nom ça ?!?");
            return Optional.empty();
        }
    }

    private Optional<Integer> validatedAge(Communication com) {
        com.send("Quel âge as-tu ?");
        String response = com.receive();
        try {
            Integer age = Integer.parseInt(response);
            return Optional.of(age);
        } catch(NumberFormatException e) {
            return Optional.empty();
        }
    }

    private Optional<Utilisateur> validatedUtilisateur(Communication com, Optional<String[]> identite, Optional<Integer> age) {
        String response;
        do {
            com.send("Tu te décris comme étant plutôt : fou de sport (tape 1), accro à la culture (tape 2), passionné de jeux (tape 3)");
            response = com.receive();
        } while(!options.contains(response));

        Utilisateur utilisateur = null;
        if(identite.isPresent() && identite.get().length == 2 && age.isPresent()) {
            String prenom = identite.get()[0];
            String nomF = identite.get()[1];
            int ageFinal = age.get();

            switch(response) {
                case "1" :
                    utilisateur = new Sportif(nomF, prenom, ageFinal, "Lille");
                    break;
                case "2":
                    utilisateur = new GrosseTete(nomF, prenom, ageFinal, "Lille");
                    break;
                case "3":
                    utilisateur = new Joueur(nomF, prenom, ageFinal, "Lille");
                    break;
            }
        }

        listeUtilisateurs.add(utilisateur);
        return Optional.ofNullable(utilisateur);
    }
}
