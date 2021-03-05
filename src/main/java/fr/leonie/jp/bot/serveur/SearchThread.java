package fr.leonie.jp.bot.serveur;

import fr.leonie.jp.bot.bot.Bot;
import fr.leonie.jp.bot.communication.Communication;
import fr.leonie.jp.bot.communication.ServeurCommunication;
import fr.leonie.jp.bot.constant.Constant;
import fr.leonie.jp.bot.utilisateurs.Utilisateur;

import java.util.Optional;

public class SearchThread extends Thread {
    private final ServeurCommunication communication;
    private final Bot bot;

    public SearchThread(ServeurCommunication communication) {
        this.communication = communication;
        this.bot = Bot.getInstance();
    }

    @Override
    public void run() {
        communication.send(Constant.textInRed("test"));

        try {
            synchronized (communication) {
                while(!communication.isCloseRequested()) {
                    communication.wait();

                    Utilisateur currentUtilisateur = communication.getCurrentUtilisateur();
                    if (currentUtilisateur != null) {
                        communication.send(Constant.textInRed(currentUtilisateur.getNom()));
                    } else {
                        communication.send(Constant.textInRed("Utilisateur inconnu"));
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
