package fr.leonie.jp.bot.serveur;

import fr.leonie.jp.bot.bot.Bot;
import fr.leonie.jp.bot.communication.Communication;
import fr.leonie.jp.bot.communication.ServeurCommunication;
import fr.leonie.jp.bot.utilisateurs.Utilisateur;

import java.util.Optional;

public class ServeurThread extends Thread {

    private final ServeurCommunication communication;
    private final Bot bot;

    public ServeurThread(ServeurCommunication communication) {
        this.communication = communication;
        this.bot = Bot.getInstance();
    }

    public void run() {

        bot.discuss(communication);

        communication.close();
    }
}
