package fr.leonie.jp.bot;

import fr.leonie.jp.bot.communication.Communication;
import fr.leonie.jp.bot.utilisateurs.*;
import fr.leonie.jp.bot.constant.Constant;
import fr.leonie.jp.bot.xml.ExportXML;
import fr.leonie.jp.bot.xml.ImportXML;
import jdk.jshell.execution.Util;

import javax.swing.text.html.Option;
import java.util.*;

public class Bot {
    private static final Bot INSTANCE = new Bot();
    private final String nom;
    private final ArrayList<Utilisateur> listeUtilisateurs;

    private final List<String> yesAnswers = Arrays.asList(Constant.getYesAnswersArray());
    private final List<String> noAnswers = Arrays.asList(Constant.getNoAnswersArray());

    private final String[] optionsArray = {"1", "2", "3"};
    private final List<String> options = Arrays.asList(optionsArray);

    private Bot() {
        nom = "MeetBot";
        listeUtilisateurs = new ArrayList<Utilisateur>();
        ImportXML.importUtilisateurs(listeUtilisateurs);
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
        com.send("Bonjour, je suis MeetBot.");

        Optional<String[]> identite;
        do {
            identite = this.validatedName(com);
        } while(identite.isEmpty());

        System.out.println("Connexion avec l'utilisateur " + identite.get()[0] + " " + identite.get()[1]);

        // retrouver l'utilisateur si déjà connu
        Optional<Utilisateur> utilisateur = this.doWeKnow(com, identite);

        // sinon, on le crée
        if(utilisateur.isEmpty()){
            Optional<Integer> age;
            do {
                age = this.validatedAge(com);
            } while(age.isEmpty());

            Optional<String> ville;
            do {
                ville = this.validatedVille(com);
            } while(ville.isEmpty());

            utilisateur = this.validatedUtilisateur(com, identite, age, ville);
        }

        // et on fait plus ample connaissance
        // attention : si utilisateur deja connu, ne pas redemander ce qu'on sait déjà


        // exportXML
        ExportXML.exportUtilisateurs(listeUtilisateurs);

        if(utilisateur.isPresent()) {
            com.send("Au revoir " + utilisateur.get().getPrenom() + " " + utilisateur.get().getNom());
        } else {
            com.send("Bye");
        }

        System.out.println("Fin de connexion avec l'utilisateur " + identite.get()[0] + " " + identite.get()[1]);
    }

    private Optional<String[]> validatedName(Communication com) {
        com.send("Comment t'appelles-tu ?");
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
            if(yesAnswers.contains(response.toLowerCase())) {
                String prenom = splitResponse[0];
                com.send("Et quel est ton nom ?");
                response = com.receive();
                if(response.length() == 0 || !response.matches("[a-zA-Z]+")) {
                    com.send("Tu te moques de moi !!");
                    return Optional.empty();
                } else {
                    return Optional.of(new String[]{prenom, response});
                }
            } else if (noAnswers.contains(response.toLowerCase())) {
                com.send("Alors c'est ton nom ?");
                response = com.receive();
                if(yesAnswers.contains(response.toLowerCase())) {
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
            Integer age = new Integer(response);
            return Optional.of(age);
        } catch(NumberFormatException e) {
            com.send("Un age avec des chiffres stp...");
            return Optional.empty();
        }
    }

    private Optional<String> validatedVille(Communication com) {
        com.send("Dans quelle ville vis-tu ?");
        String response = com.receive();

        if(response == null || !response.matches("[a-zA-Z]+")) {
            com.send("Tu te moques de moi !!");
            return Optional.empty();
        } else {
            return Optional.of(response);
        }
    }

    private Optional<Utilisateur> validatedUtilisateur(Communication com, Optional<String[]> identite, Optional<Integer> age, Optional<String> ville) {
        String response;
        do {
            com.send("Tu te décris comme étant plutôt :");
            com.send("fou de sport (tape 1),");
            com.send("accro à la culture (tape 2),");
            com.send("passionné de jeux (tape 3)");
            response = com.receive();
        } while(!options.contains(response));

        Utilisateur utilisateur = null;
        if(identite.isPresent() && identite.get().length == 2 && age.isPresent() && ville.isPresent()) {
            String prenom = identite.get()[0];
            String nomF = identite.get()[1];
            int ageFinal = age.get();
            String villeFinale = ville.get();

            switch(response) {
                case "1" :
                    utilisateur = new Sportif(nomF, prenom, ageFinal, villeFinale);
                    break;
                case "2":
                    utilisateur = new GrosseTete(nomF, prenom, ageFinal, villeFinale);
                    break;
                case "3":
                    utilisateur = new Joueur(nomF, prenom, ageFinal, villeFinale);
                    break;
            }
        }

        listeUtilisateurs.add(utilisateur);
        return Optional.ofNullable(utilisateur);
    }

    private Optional<Utilisateur> doWeKnow(Communication com, Optional<String[]> identite) {
        for(Utilisateur utilisateur : listeUtilisateurs) {
            if(utilisateur.getPrenom().equals(identite.get()[0]) && utilisateur.getNom().equals(identite.get()[1])) {
                com.send("Etes-vous " + utilisateur.getPrenom() + " " + utilisateur.getNom() + ", " + utilisateur.getAge() + " ans, vivant à " + utilisateur.getVille() + " ?");
                String response = com.receive();
                if(yesAnswers.contains(response.toLowerCase())) {
                    com.send("Ravi de te revoir !");
                    return Optional.of(utilisateur);
                } else if(noAnswers.contains(response.toLowerCase())) {
                    com.send("Ah, j'ai dû confondre...");
                }
            }
        }
        com.send("Enchanté de faire ta connaissance");
        return Optional.empty();
    }
}
