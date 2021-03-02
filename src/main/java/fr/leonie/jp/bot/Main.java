package fr.leonie.jp.bot;

public class Main {
    public static void main(String[] args) {
        Bot bot = Bot.getInstance();
        Thread botThread = new Thread(bot);
        botThread.start();
    }
}
