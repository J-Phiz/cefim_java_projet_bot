package fr.leonie.jp.bot.serveur;

import fr.leonie.jp.bot.communication.Communication;
import fr.leonie.jp.bot.communication.ServeurCommunication;
import fr.leonie.jp.bot.constant.Constant;

import java.io.IOException;
import java.net.ServerSocket;

public class ServeurMain {

    private ServerSocket serverSocket;

    public ServeurMain() {
        try {
            this.serverSocket = new ServerSocket(Constant.getPort());

        } catch (IOException ex) {
            System.out.println("I/O erreur: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {
        System.out.println();
        System.out.println();
        System.out.println("Bot démarré et en attente de connexions.");

        while (true) {
            Communication communication = new ServeurCommunication(serverSocket);
            communication.open();

            new ServeurThread(communication).start();
        }

    }

}
