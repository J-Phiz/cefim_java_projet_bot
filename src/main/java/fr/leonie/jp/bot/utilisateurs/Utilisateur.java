package fr.leonie.jp.bot.utilisateurs;

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
    private String jeuPrefere;

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

    public String getJeuPrefere() {
        return jeuPrefere;
    }

    public abstract ArrayList<Loisir> getListeLoisirs();

    public abstract String getLoisirCategory();

    public void talkAbout(Communication com) throws IOException {
        String type = getLoisirCategory();
        String response;

        com.send("Quel est ton " + type + " préféré ?");
        jeuPrefere = com.receive();
        com.send("Moi aussi j'adore !");

        com.send("En moyenne, tu " + (type.compareTo(Jeu.getCategory()) == 0 ? "joues" : "en fais") + " combien d'heures par semaine ?");
        response = com.receive();
        try {
            frequence = new Integer(response);
        } catch(NumberFormatException e) {
            com.send("Une réponse avec des chiffres stp...");
        }
        com.send("Ah c'est pas mal");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return Objects.equals(nom.toLowerCase(), that.nom.toLowerCase()) && Objects.equals(prenom.toLowerCase(), that.prenom.toLowerCase());
    }
}
