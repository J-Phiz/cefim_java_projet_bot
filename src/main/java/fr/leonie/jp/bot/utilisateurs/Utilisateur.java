package fr.leonie.jp.bot.utilisateurs;

import fr.leonie.jp.bot.bot.BotTools;
import fr.leonie.jp.bot.communication.Communication;
import fr.leonie.jp.bot.constant.Constant;
import fr.leonie.jp.bot.loisirs.Jeu;
import fr.leonie.jp.bot.loisirs.Loisir;

import java.io.IOException;
import java.util.*;

public abstract class Utilisateur {
    private final String nom;
    private final String prenom;
    private final int age;
    private final String ville;

    private final List<String> options = Arrays.asList(Constant.getOptionsArray());
    private int frequence;
    private String loisirPrefere;
    private String sdb;

    protected Utilisateur(String pNom, String pPrenom, int pAge, String pVille) {
        nom = pNom;
        prenom = pPrenom;
        age = pAge;
        ville = pVille;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getAge() {
        return age;
    }

    public String getVille() {
        return ville;
    }

    public int getFrequence() {
        return frequence;
    }

    public String getLoisirPrefere() {
        return loisirPrefere;
    }

    public void setFrequence(int frequence) {
        this.frequence = frequence;
    }

    public void setLoisirPrefere(String loisirPrefere) {
        this.loisirPrefere = loisirPrefere;
    }

    public String getSdb() {
        return sdb;
    }

    public void setSdb(String sdb) {
        this.sdb = sdb;
    }

    public abstract ArrayList<Loisir> getListeLoisirs();

    public abstract String getLoisirCategory();

    public void talkAbout(Communication com) throws IOException {
        String type = getLoisirCategory();

        if(Constant.isNullOrEmpty(loisirPrefere)) {
            loisirPrefere = BotTools.responseInList(com, this.getListeLoisirs(), "Quel est ton " + type + " préféré ?");
            com.send("Moi aussi j'adore !");
        }
        if(frequence == 0) {
            frequence = BotTools.responseInt(com, "En moyenne, tu " + (type.compareTo(Jeu.getCategory()) == 0 ? "joues" : "en fais") + " combien d'heures par semaine ?");
            com.send("Ah c'est pas mal");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return Objects.equals(nom.toLowerCase(), that.nom.toLowerCase()) &&
                Objects.equals(prenom.toLowerCase(), that.prenom.toLowerCase()) &&
                Objects.equals(ville.toLowerCase(), that.ville.toLowerCase()) &&
                age == that.age;
    }
}
