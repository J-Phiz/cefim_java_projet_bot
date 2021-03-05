package fr.leonie.jp.bot.serveur;

import fr.leonie.jp.bot.bot.Bot;
import fr.leonie.jp.bot.communication.Communication;
import fr.leonie.jp.bot.constant.Constant;

public class SearchThread extends Thread {
    private final Communication communication;
    private final Bot bot;

    public SearchThread(Communication communication) {
        this.communication = communication;
        this.bot = Bot.getInstance();
    }

    @Override
    public void run() {
        communication.send(Constant.textInRed("test"));
    }
}
