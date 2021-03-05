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

            Utilisateur currentUtilisateur = new Utilisateur("TOTO", "Machin", 3, "SaisPas") {
                @Override
                public ArrayList<Loisir> getListeLoisirs() {
                    return null;
                }

                @Override
                public String getLoisirCategory() {
                    return null;
                }
            };
            System.out.println("Main: " + currentUtilisateur);
            new ServeurThread(communication, currentUtilisateur).start();
            new SearchThread(communication, currentUtilisateur).start();
        }

    }

}
