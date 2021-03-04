package fr.leonie.jp.bot;

import fr.leonie.jp.bot.communication.Communication;
import fr.leonie.jp.bot.loisirs.Jeu;
import fr.leonie.jp.bot.loisirs.Loisir;
import fr.leonie.jp.bot.loisirs.LoisirFactory;
import fr.leonie.jp.bot.loisirs.Sport;
import fr.leonie.jp.bot.utilisateurs.*;
import fr.leonie.jp.bot.constant.Constant;
import fr.leonie.jp.bot.xml.ExportXML;
import fr.leonie.jp.bot.xml.ImportXML;
import jdk.jshell.execution.Util;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.*;

public class Bot {
    private static final Bot INSTANCE = new Bot();
    private final String nom;
    private final ArrayList<Utilisateur> listeUtilisateurs;
    private final ArrayList<Sport> listeSports;
    private final ArrayList<Jeu> listeJeux;

    private final List<String> yesAnswers = Arrays.asList(Constant.getYesAnswersArray());
    private final List<String> noAnswers = Arrays.asList(Constant.getNoAnswersArray());

    private final List<String> options = Arrays.asList(Constant.getOptionsArray());

    private Bot() {
        nom = "MeetBot";
        listeUtilisateurs = new ArrayList<>();
        listeSports = new ArrayList<>();
        listeJeux = new ArrayList<>();
        ImportXML.importSports(listeSports);
        ImportXML.importJeux(listeJeux);
        ImportXML.importUtilisateurs(listeUtilisateurs, listeSports, listeJeux);
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
        com.send("Bonjour, je suis " + this.getNom());

        Optional<String[]> identite = Optional.empty();

        try {
            do {
                identite = this.validatedName(com);
            } while (identite.isEmpty());

            System.out.println("Connexion avec l'utilisateur " + identite.get()[0] + " " + identite.get()[1]);

            // retrouver l'utilisateur si déjà connu
            Optional<Utilisateur> utilisateur = this.doWeKnow(com, identite);

            // sinon, on le crée
            if (utilisateur.isEmpty()) {
                Optional<Integer> age;
                do {
                    age = this.validatedAge(com);
                } while (age.isEmpty());

                Optional<String> ville;
                do {
                    ville = this.validatedVille(com);
                } while (ville.isEmpty());

                utilisateur = this.validatedUtilisateur(com, identite, age, ville);
            }

            // et on fait plus ample connaissance
            // attention : si utilisateur deja connu, ne pas redemander ce qu'on sait déjà
            if (utilisateur.isPresent()) {
                this.getToKnowBetter(com, utilisateur.get());
            }

            if(utilisateur.isPresent()) {
                com.send("A bientôt " + utilisateur.get().getPrenom() + " " + utilisateur.get().getNom());
            } else {
                com.send("A bientôt");
            }
            com.send("bye");

            System.out.println("Fin de connexion avec l'utilisateur " + identite.get()[0] + " " + identite.get()[1]);

        } catch (IOException ex) {
            if (identite.isPresent()) {
                System.out.println("Connexion Fermée par l'utilisateur " + identite.get()[0] + " " + identite.get()[1]);
            }
        }

        // exportXML
        if(listeUtilisateurs.size() > 0) {
            ExportXML.exportUtilisateurs(listeUtilisateurs);
        }
        if(listeSports.size() > 0) {
            ExportXML.exportSports(listeSports);
        }
        if(listeJeux.size() > 0) {
            ExportXML.exportJeux(listeJeux);
        }
    }

    private Optional<String[]> validatedName(Communication com) throws IOException {
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

    private Optional<Integer> validatedAge(Communication com) throws IOException {
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

    private Optional<String> validatedVille(Communication com) throws IOException {
        com.send("Dans quelle ville vis-tu ?");
        String response = com.receive();

        if(response == null || !response.matches("[a-zA-Z]+")) {
            com.send("Tu te moques de moi !!");
            return Optional.empty();
        } else {
            return Optional.of(response);
        }
    }

    private Optional<Utilisateur> validatedUtilisateur(Communication com, Optional<String[]> identite, Optional<Integer> age, Optional<String> ville) throws IOException {
        String response;
        do {
            com.send("Tu te décris comme étant plutôt :");
            com.send("fou de sport (tape 1),");
            com.send("passionné de jeux (tape 2)");
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
                    utilisateur = new Joueur(nomF, prenom, ageFinal, villeFinale);
                    break;
            }
        }

        Utilisateur finalUtilisateur = utilisateur;
        if(listeUtilisateurs.stream().noneMatch(u -> u.equals(finalUtilisateur))) {
            listeUtilisateurs.add(utilisateur);
        }
        return Optional.ofNullable(utilisateur);
    }

    private Optional<Utilisateur> doWeKnow(Communication com, Optional<String[]> identite) throws IOException {
        for(Utilisateur utilisateur : listeUtilisateurs) {
            if(utilisateur.getPrenom().equalsIgnoreCase(identite.get()[0]) && utilisateur.getNom().equalsIgnoreCase(identite.get()[1])) {
                com.send("Etes-vous " + utilisateur.getPrenom() + " " + utilisateur.getNom() + ", " + utilisateur.getAge() + " ans, vivant à " + utilisateur.getVille() + " ?");
                String response = com.receive();
                if(yesAnswers.contains(response.toLowerCase())) {
                    com.send("Ravi de te retrouver !");
                    if(utilisateur.getLoisirPrefere() != null) {
                        com.send("Tu aimes toujours le/la " + utilisateur.getLoisirPrefere() + " ?");
                        response = com.receive();
                        if(yesAnswers.contains(response.toLowerCase())) {
                            com.send("C'est une excellente nouvelle !");
                        } else if(noAnswers.contains(response.toLowerCase())) {
                            com.send("Dommage, on en reparle plus tard alors");
                        } else {
                            com.send("C'était pas trop dur comme question pourtant, ça commence bien ...");
                        }
                    }
                    return Optional.of(utilisateur);
                } else if(noAnswers.contains(response.toLowerCase())) {
                    com.send("Ah, j'ai dû confondre...");
                }
            }
        }
        com.send("Enchanté de faire ta connaissance");
        return Optional.empty();
    }

    private void getToKnowBetter(Communication com, Utilisateur utilisateur) throws IOException {
        String response = "0";
        while(!response.equals("bain") && !response.equals("douche")) {
            com.send("Avant tout, je voudrais savoir : plutôt bain ou douche ?");
            response = com.receive();
        }
        utilisateur.setSdb(response);

        String categoryDeLoisir = utilisateur.getLoisirCategory();
        Integer nbLoisirs = null;
        while(nbLoisirs == null) {
            com.send("De combien de " + categoryDeLoisir.toLowerCase() + "(s/x) veux-tu me parler ?");
            response = com.receive();
            try {
               nbLoisirs = new Integer(response);
            } catch(NumberFormatException e) {
                com.send("Une réponse avec des chiffres stp...");
            }
        };

        for(int i = 0; i < nbLoisirs; i++) {
            com.send("Quel est le nom de ton " + categoryDeLoisir.toLowerCase() + " ?");
            String nom = com.receive();

            // chercher dans la liste des loisirs...
            Optional<? extends Loisir> loisir = this.doIKnowThisHobby(categoryDeLoisir, nom);
            boolean newHobby = false;

            if(loisir.isEmpty()) {
                do {
                    com.send("Je ne connais pas, tu fais ça seul ?");
                    newHobby = true;
                    String solo = com.receive();
                    int nbParticipants = 1;

                    if(noAnswers.contains(solo.toLowerCase())) {
                        com.send("A combien alors ?");
                        nbParticipants = Integer.parseInt(com.receive());
                        loisir = Optional.of(LoisirFactory.getLoisir(nom, nbParticipants, categoryDeLoisir));
                    } else if (!yesAnswers.contains(solo.toLowerCase())) {
                        com.send("C'est une question simple, peux-tu répondre par oui ou par non...");
                    } else {
                        loisir = Optional.of(LoisirFactory.getLoisir(nom, nbParticipants, categoryDeLoisir));
                    }
                } while(loisir.isEmpty());
            }

            if(newHobby) {
                if(loisir.get().getClass().getSimpleName().equals("Sport")) {
                    listeSports.add((Sport) loisir.get());
                } else if(loisir.get().getClass().getSimpleName().equals("Jeu")) {
                    listeJeux.add((Jeu) loisir.get());
                }
            }

            Loisir loisirFinal = loisir.get();

            if(utilisateur.getListeLoisirs().stream().noneMatch(l -> l.equals(loisirFinal))) {
                utilisateur.getListeLoisirs().add(loisir.get());
            }
        }

        utilisateur.talkAbout(com);
    }

    private <T extends Loisir> Optional<T> doIKnowThisHobby(String categoryDeLoisir, String nom) {
        ArrayList<? extends Loisir> listeLoisirs = new ArrayList<>();
        switch(categoryDeLoisir) {
            case "jeu":
                listeLoisirs = listeJeux;
                break;
            case "sport":
                listeLoisirs = listeSports;
                break;
        }

        for(int i = 0; i < listeLoisirs.size(); i++) {
            if(listeLoisirs.get(i).getName().equalsIgnoreCase(nom)) {
                return Optional.of((T) listeLoisirs.get(i));
            }
        }
        return Optional.empty();
    }
}
