package fr.leonie.jp.bot.serveur;

import fr.leonie.jp.bot.bot.Bot;
import fr.leonie.jp.bot.communication.Communication;
import fr.leonie.jp.bot.utilisateurs.Utilisateur;

public class ServeurThread extends Thread {

    private final Communication communication;
    private Utilisateur utilisateur;
    private final Bot bot;

    public ServeurThread(Communication communication, Utilisateur utilisateur) {
        this.communication = communication;
        this.utilisateur = utilisateur;
        this.bot = Bot.getInstance();
        System.out.println("Serveur Const: " + utilisateur);
    }

    public void run() {

        bot.discuss(communication, utilisateur);

        communication.close();
    }
}
