package fr.leonie.jp.bot.serveur;

import fr.leonie.jp.bot.bot.Bot;
import fr.leonie.jp.bot.communication.Communication;

public class ServeurThread extends Thread {

    private final Communication communication;
    private final Bot bot;

    public ServeurThread(Communication communication) {
        this.communication = communication;
        this.bot = Bot.getInstance();
    }

    public void run() {

        bot.discuss(communication);

        communication.close();
    }
}
