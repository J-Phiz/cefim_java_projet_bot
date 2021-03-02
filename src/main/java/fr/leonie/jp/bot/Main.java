package fr.leonie.jp.bot;

import fr.leonie.jp.bot.client.ClientMain;
import fr.leonie.jp.bot.serveur.ServeurMain;

public class Main {
    public static void main(String[] args) {

        if ("serveur".equals(args[0])) {
            ServeurMain serveur = new ServeurMain();
            serveur.run();
        }

        if ("client".equals(args[0])) {
            ClientMain client = new ClientMain();
            client.run();
        }

    }
}
