package fr.leonie.jp.bot.utilisateurs;

import fr.leonie.jp.bot.loisirs.Loisir;
import fr.leonie.jp.bot.loisirs.Sport;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public abstract class Utilisateur {
    private final String nom;
    private final String prenom;
    private final int age;
    private final String ville;

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

    public abstract ArrayList<? extends Loisir> getListeLoisirs();

    public abstract String getLoisirCategory();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return Objects.equals(nom.toLowerCase(), that.nom.toLowerCase()) && Objects.equals(prenom.toLowerCase(), that.prenom.toLowerCase());
    }
}
