package fr.leonie.jp.bot.utilisateurs;

public abstract class Utilisateur {
    private final String nom;
    private final String prenom;
    private final int age;
    private final String ville;

    public Utilisateur(String pNom, String pPrenom, int pAge, String pVille) {
        nom = pNom;
        prenom = pPrenom;
        age = pAge;
        ville = pVille;
    }
}
