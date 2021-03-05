package fr.leonie.jp.bot.serveur;

import fr.leonie.jp.bot.bot.Bot;
import fr.leonie.jp.bot.communication.Communication;
import fr.leonie.jp.bot.constant.Constant;
import fr.leonie.jp.bot.utilisateurs.Utilisateur;

public class SearchThread extends Thread {
    private final Communication communication;
    private Utilisateur utilisateur;
    private final Bot bot;

    public SearchThread(Communication communication, Utilisateur utilisateur) {
        this.communication = communication;
        this.utilisateur = utilisateur;
        this.bot = Bot.getInstance();
        System.out.println("Search Const: " + utilisateur);
    }

    @Override
    public void run() {
        communication.send(Constant.textInRed("test"));

        try {
            synchronized (communication) {
                communication.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (utilisateur) {
            System.out.println("Search: " + utilisateur);
            if (utilisateur != null) {
                communication.send(Constant.textInRed(utilisateur.getNom()));
            } else {
                communication.send(Constant.textInRed("Utilisateur inconnu"));
            }
        }

    }
}
