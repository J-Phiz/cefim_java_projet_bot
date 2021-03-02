package fr.leonie.jp.bot.serveur;

import fr.leonie.jp.bot.Communication;
import fr.leonie.jp.bot.constant.Constant;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
        System.out.println("Bot démarré et en attente de connexions.");

        while (true) {
            Communication communication = new ServeurCommunication(serverSocket);
            communication.open();

            new ServeurThread(communication).start();
        }

    }

}
