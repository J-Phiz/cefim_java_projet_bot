package fr.leonie.jp.bot.bot;

import fr.leonie.jp.bot.communication.Communication;
import fr.leonie.jp.bot.loisirs.Jeu;
import fr.leonie.jp.bot.loisirs.Loisir;
import fr.leonie.jp.bot.loisirs.LoisirFactory;
import fr.leonie.jp.bot.loisirs.Sport;
import fr.leonie.jp.bot.search.Search;
import fr.leonie.jp.bot.utilisateurs.*;
import fr.leonie.jp.bot.constant.Constant;
import fr.leonie.jp.bot.xml.ExportXML;
import fr.leonie.jp.bot.xml.ImportXML;

import java.io.IOException;
import java.util.*;

public class Bot {
    private static final Bot INSTANCE = new Bot();
    private final String nom;
    private final ArrayList<Utilisateur> listeUtilisateurs;
    private final ArrayList<Sport> listeSports;
    private final ArrayList<Jeu> listeJeux;

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

    public ArrayList<Utilisateur> getListeUtilisateurs() { return listeUtilisateurs; }

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
                int age = this.validatedAge(com);

                Optional<String> ville;
                do {
                    ville = this.validatedVille(com);
                } while (ville.isEmpty());

                utilisateur = this.validatedUtilisateur(com, identite, age, ville);
            }

            // et on fait plus ample connaissance
            if(utilisateur.isPresent()) {
                String[] msg = {
                        "Tu préfères rechercher des amis ? Tape 1",
                        "ou continuer à compléter ton profil ? Tape 2"
                };
                int choice = BotTools.responseOption(com, 2,msg);

                switch(choice) {
                    case 1 -> this.paramSearch(com, utilisateur.get());
                    case 2 -> this.getToKnowBetter(com, utilisateur.get());
                }

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
            if(BotTools.responseYesNo(com, "C'est ton prénom ?")) {
                String prenom = splitResponse[0];
                com.send("Et quel est ton nom ?");
                response = com.receive();
                if(response.length() == 0 || !response.matches("[a-zA-Z]+")) {
                    com.send("Tu te moques de moi !!");
                    return Optional.empty();
                } else {
                    return Optional.of(new String[]{prenom, response});
                }
            } else {
                if(BotTools.responseYesNo(com, "Alors c'est ton nom ?")) {
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
            }
        } else {
            com.send("C'est un nom ça ?!?");
            return Optional.empty();
        }
    }

    private int validatedAge(Communication com) throws IOException {
        return BotTools.responseInt(com, "Quel âge as-tu ?");
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

    private Optional<Utilisateur> validatedUtilisateur(Communication com, Optional<String[]> identite, int age, Optional<String> ville) throws IOException {
        String response = "";
        String[] msgs = {
                "Tu te décris comme étant plutôt :",
                "fou de sport (tape 1),",
                "passionné de jeux (tape 2)"
        };
        int choice = BotTools.responseOption(com, 2, msgs);
        switch (choice) {
            case 1 -> response = "sport";
            case 2 -> response = "jeu";
        }

        Utilisateur utilisateur = null;
        if(identite.isPresent() && identite.get().length == 2 && ville.isPresent()) {
            String prenom = identite.get()[0];
            String nomF = identite.get()[1];
            int ageFinal = age;
            String villeFinale = ville.get();

            switch(response) {
                case "sport" :
                    utilisateur = new Sportif(nomF, prenom, ageFinal, villeFinale);
                    break;
                case "jeu":
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
                if(BotTools.responseYesNo(com, "Etes-vous " + utilisateur.getPrenom() + " " + utilisateur.getNom() + ", " + utilisateur.getAge() + " ans, vivant à " + utilisateur.getVille() + " ?")) {
                    com.send("Ravi de te retrouver !");
                    if(utilisateur.getLoisirPrefere() != null) {
                        if(BotTools.responseYesNo(com,"Tu aimes toujours le/la " + utilisateur.getLoisirPrefere() + " ?")) {
                            com.send("C'est une excellente nouvelle !");
                        } else {
                            com.send("Dommage, on en reparle plus tard alors");
                        }
                    }
                    return Optional.of(utilisateur);
                } else {
                    com.send("Ah, j'ai dû confondre...");
                }
            }
        }
        com.send("Enchanté de faire ta connaissance");
        return Optional.empty();
    }

    private void getToKnowBetter(Communication com, Utilisateur utilisateur) throws IOException {
        if(Constant.isNullOrEmpty(utilisateur.getSdb())) {
            String response = "";
            // Question existentielle
            String[] msgs = {
                    "Avant tout, je voudrais savoir : plutôt bain ou douche ?",
                    "les douches c'est la vie (tape 1),",
                    "les bains c'est trop bien (tape 2)"
            };
            int choice = BotTools.responseOption(com, 2, msgs);
            switch (choice) {
                case 1 -> response = "douche";
                case 2 -> response = "bain";
            }
            utilisateur.setSdb(response);
            if(utilisateur.getSdb().equals("douche")) {
                com.send("Excellent choix car les baignoires ça fuit !!!!");
            } else {
                com.send("Ahh fais attention les baignoires ça fuit !!!!");
            }
        }

        String categoryDeLoisir = utilisateur.getLoisirCategory();
        if(BotTools.responseYesNo(com,"Tu veux ajouter des " + categoryDeLoisir.toLowerCase() + "(s/x) à ton profil ?")) {
            int nbLoisirs = BotTools.responseInt(com, "De combien de " + categoryDeLoisir.toLowerCase() + "(s/x) veux-tu me parler ?");
            for(int i = 0; i < nbLoisirs; i++) {
                com.send("Quel est le nom de ton " + categoryDeLoisir.toLowerCase() + " n°" + (i+1) +" ?");
                String nom = com.receive();

                // chercher dans la liste des loisirs...
                Optional<? extends Loisir> loisir = this.doIKnowThisHobby(categoryDeLoisir, nom);
                boolean newHobby = false;

                if(loisir.isEmpty()) {
                    newHobby = true;
                    int nbParticipants;

                    if(BotTools.responseYesNo(com,"Je ne connais pas, tu fais ça seul ?")) {
                        nbParticipants = 1;
                    } else {
                        nbParticipants = BotTools.responseInt(com, "A combien alors ?");
                    }
                    loisir = Optional.of(LoisirFactory.getLoisir(nom, nbParticipants, categoryDeLoisir));
                } else {
                    com.send("C'est un excellent choix");
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

                if(i < nbLoisirs-1) {
                    com.send("Passons à la suite tu veux bien...");
                }
            }
        } else {
            com.send("Peut-être la prochaine fois :)");
        }

        utilisateur.talkAbout(com);

        if(BotTools.responseYesNo(com, "Maintenant que je te connais mieux, je te présente des amis ?")) {
            this.paramSearch(com, utilisateur);
        }
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

    private void paramSearch(Communication com, Utilisateur utilisateur) throws IOException {
        boolean ville = BotTools.responseYesNo(com, "Uniquement des amis qui vivent dans ta ville ?");
        int age = BotTools.responseInt(com, "Quelle différence d'age maximum ?");
        Search.SearchBuilder recherche = new Search.SearchBuilder(utilisateur, ville, age);
        if(utilisateur.getListeLoisirs().size() > 0) {
            int nbCommonLoisirs = BotTools.responseInt(com, "Combien de loisirs en commun ?");
            recherche.nbCommonLoisirs(nbCommonLoisirs);
        }
        if(!Constant.isNullOrEmpty(utilisateur.getSdb())) {
            boolean sdb = BotTools.responseYesNo(com, "Est-ce que tu ne sympathises qu'avec des personnes qui prennent des " + utilisateur.getSdb() + "s comme toi ?");
            recherche.sdb(sdb);
        }

        ArrayList<Utilisateur> resultat = recherche.build().result();
        if(resultat.size() > 0) {
            com.send(Constant.textInRed("Voici les personnes que j'ai sélectionnées pour toi :"));
            for(Utilisateur u : resultat) {
                com.send(Constant.textInRed(u.getPrenom() + " " + u.getNom() + " qui adore le/la " + u.getLoisirPrefere()));
            }
        } else {
            com.send(Constant.textInRed("J'ai une bonne et une mauvaise nouvelle"));
            com.send(Constant.textInRed("La mauvaise : je n'ai aucun résultat"));
            com.send(Constant.textInRed("La bonne : moi, je veux bien être ton ami <3"));
        }
    }
}
