package fr.leonie.jp.bot.utilisateurs;

import fr.leonie.jp.bot.communication.Communication;
import fr.leonie.jp.bot.constant.Constant;
import fr.leonie.jp.bot.loisirs.Jeu;
import fr.leonie.jp.bot.loisirs.Loisir;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Joueur extends Utilisateur {
    private ArrayList<Loisir> jeux;
    private ArrayList<Jeu> jeux;
    private String periodeJeu;
    private int moyenneNbPers;
    private String mange;
    private String bois;

    private final List<String> yesAnswers = Arrays.asList(Constant.getYesAnswersArray());
    private final List<String> noAnswers = Arrays.asList(Constant.getNoAnswersArray());
    private final List<String> options = Arrays.asList(Constant.getOptionsArray());

    public Joueur(String pNom, String pPrenom, int pAge, String pVille) {
        super(pNom, pPrenom, pAge, pVille);
        jeux = new ArrayList<Loisir>();
    }

    @Override
    public void talkAbout(Communication com) throws IOException {
        super.talkAbout(com);

        String response;

        do {
            com.send("Tu joues plutôt ?");
            com.send("en journée (tape 1),");
            com.send("en soirée (tape 2)");
            response = com.receive();
        } while(!options.contains(response));
        switch(response) {
            case "1" :
                periodeJeu = "journée";
                break;
            case "2":
                periodeJeu = "soirée";
                break;
        }
        com.send("Tu as raison, en " + periodeJeu + " c'est le meilleur moment !");

        com.send("En moyenne, tu joues avec combien de personnes ?");
        response = com.receive();
        try {
            moyenneNbPers = new Integer(response);
        } catch(NumberFormatException e) {
            com.send("Une réponse avec des chiffres stp...");
        }

        com.send("Quand tu joues, tu grignottes ?");
        response = com.receive();
        mange = "rien";
        if(noAnswers.contains(response.toLowerCase())) {
            com.send("Tu devrais essayer c'est sympa !");
        } else if (!yesAnswers.contains(response.toLowerCase())) {
            com.send("C'est une question simple, peux-tu répondre par oui ou par non...");
        } else {
            com.send("Et du coup tu manges quoi ?");
            mange = com.receive();
        }

        com.send("Quand tu joues, tu bois quoi ?");
        bois = com.receive();
    }

    @Override
    public ArrayList<Loisir> getListeLoisirs() {
        return jeux;
    }

    @Override
    public String getLoisirCategory() {
        return Jeu.getCategory();
    }

    public String getPeriodeJeu() {
        return periodeJeu;
    }

    public int getMoyenneNbPers() {
        return moyenneNbPers;
    }

    public String getMange() {
        return mange;
    }

    public String getBois() {
        return bois;
    }
}
