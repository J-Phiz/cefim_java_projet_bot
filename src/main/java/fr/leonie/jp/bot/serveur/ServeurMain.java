package fr.leonie.jp.bot.serveur;

import fr.leonie.jp.bot.communication.Communication;
import fr.leonie.jp.bot.communication.ServeurCommunication;
import fr.leonie.jp.bot.constant.Constant;
import fr.leonie.jp.bot.loisirs.Loisir;
import fr.leonie.jp.bot.utilisateurs.Sportif;
import fr.leonie.jp.bot.utilisateurs.Utilisateur;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Optional;

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
            Utilisateur currentUtilisateur = null;
            ServeurCommunication communication = new ServeurCommunication(serverSocket, currentUtilisateur);
            communication.open();

            new ServeurThread(communication).start();
            new SearchThread(communication).start();
        }

    }

}
