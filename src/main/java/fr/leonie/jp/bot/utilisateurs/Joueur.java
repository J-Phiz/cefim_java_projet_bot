package fr.leonie.jp.bot.utilisateurs;

import fr.leonie.jp.bot.bot.BotTools;
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
    private String periodeJeu;
    private int moyenneNbPers;
    private String mange;
    private String bois;


    public Joueur(String pNom, String pPrenom, int pAge, String pVille) {
        super(pNom, pPrenom, pAge, pVille);
        jeux = new ArrayList<Loisir>();
    }

    @Override
    public void talkAbout(Communication com) throws IOException {
        super.talkAbout(com);

        String response;
        int choice;
        boolean yesNo;

        // Question sur la période de Jeu
        String[] msgs = {
                "Tu joues plutôt ?",
                "en journée (tape 1),",
                "en soirée (tape 2)"
        };
        choice = BotTools.responseOption(com, 2, msgs);
        switch (choice) {
            case 1 -> periodeJeu = "journée";
            case 2 -> periodeJeu = "soirée";
        }
        com.send("Tu as raison, en " + periodeJeu + " c'est le meilleur moment !");

        // Question sur le nb personnes lors des jeux
        moyenneNbPers = BotTools.responseInt(com, "En moyenne, tu joues avec combien de personnes ?");

        // Question manger
        if(BotTools.responseYesNo(com, "Quand tu joues, tu grignottes ?")) {
            com.send("Et du coup tu manges quoi ?");
            mange = com.receive();
        } else {
            mange = "rien";
            com.send("Tu devrais essayer c'est sympa !");
        }

        // Question boire
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
