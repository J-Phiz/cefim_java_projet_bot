package fr.leonie.jp.bot.utilisateurs;

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

        do {
            com.send("Tu fais du sport plutôt ?");
            com.send("en semaine (tape 1),");
            com.send("le week-end (tape 2)");
            response = com.receive();
        } while(!options.contains(response));
        switch(response) {
            case "1" :
                periodeSport = "semaine";
                break;
            case "2":
                periodeSport = "week-end";
                break;
        }
        com.send("Tu as raison, en " + periodeSport + " c'est le meilleur moment !");

        com.send("Quand tu fais du sport, ta fréquence cardiaque monte à combien ?");
        cardioSport = Integer.parseInt(com.receive());
        com.send("Oh impressionnant");
        com.send("Et au repos du coup, ta fréquence cardiaque est à combien ?");
        cardioRepos = Integer.parseInt(com.receive());
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
