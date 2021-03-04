package fr.leonie.jp.bot.utilisateurs;

import fr.leonie.jp.bot.bot.BotTools;
import fr.leonie.jp.bot.communication.Communication;
import fr.leonie.jp.bot.constant.Constant;
import fr.leonie.jp.bot.loisirs.Loisir;
import fr.leonie.jp.bot.loisirs.Sport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sportif extends Utilisateur {
    private ArrayList<Loisir> sports;
    private String periodeSport;
    private int cardioSport;
    private int cardioRepos;

    private final List<String> options = Arrays.asList(Constant.getOptionsArray());

    public Sportif(String pNom, String pPrenom, int pAge, String pVille) {
        super(pNom, pPrenom, pAge, pVille);
        sports = new ArrayList<Loisir>();
    }

    @Override
    public ArrayList<Loisir> getListeLoisirs() {
        return sports;
    }

    @Override
    public String getLoisirCategory() {
        return Sport.getCategory();
    }

    @Override
    public void talkAbout(Communication com) throws IOException {
        super.talkAbout(com);

        String response;
        int choice;

        // Question sur la période de Sport
        String[] msgs = {
            "Tu fais du sport plutôt ?",
            "en semaine (tape 1),",
            "le week-end (tape 2)"
        };
        choice = BotTools.responseOption(com, 2, msgs);
        switch (choice) {
            case 1 -> periodeSport = "semaine";
            case 2 -> periodeSport = "week-end";
        }
        com.send("Moi aussi je préfère ce moment là !");

        // Question sur la fréquence cardiaque durant le Sport
        cardioSport = BotTools.responseInt(com, "Quand tu fais du sport, ta fréquence cardiaque monte à combien ?");
        com.send("Oh impressionnant");

        // Question sur la fréquence cardiaque durant le repos
        cardioRepos = BotTools.responseInt(com, "Et au repos du coup, ta fréquence cardiaque est à combien ?");
    }

    public String getPeriodeSport() {
        return periodeSport;
    }

    public int getCardioSport() {
        return cardioSport;
    }

    public int getCardioRepos() {
        return cardioRepos;
    }

    public void setPeriodeSport(String periodeSport) {
        this.periodeSport = periodeSport;
    }

    public void setCardioSport(int cardioSport) {
        this.cardioSport = cardioSport;
    }

    public void setCardioRepos(int cardioRepos) {
        this.cardioRepos = cardioRepos;
    }
}
