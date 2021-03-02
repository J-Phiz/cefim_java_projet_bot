package fr.leonie.jp.bot.utilisateurs;

public abstract class Utilisateur {
    private final String nom;
    private final String prenom;
    private final int age;

    public Utilisateur(String pNom, String pPrenom, int pAge) {
        nom = pNom;
        prenom = pPrenom;
        age = pAge;
    }
}
