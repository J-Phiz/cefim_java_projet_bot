package fr.leonie.jp.bot.serveur;

import fr.leonie.jp.bot.bot.Bot;
import fr.leonie.jp.bot.communication.ServeurCommunication;
import fr.leonie.jp.bot.constant.Constant;
import fr.leonie.jp.bot.search.Search;
import fr.leonie.jp.bot.utilisateurs.Utilisateur;

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
                        Search.SearchBuilder recherche = new Search.SearchBuilder(currentUtilisateur, true, 10);
                        if(!Constant.isNullOrEmpty(currentUtilisateur.getSdb())) {
                            recherche.sdb(true);
                        }
                        if(currentUtilisateur.getListeLoisirs().size() > 0) {
                            recherche.nbCommonLoisirs(1);
                        }
                        communication.send(Constant.textInRed("Pour info, " + currentUtilisateur.getPrenom() + ", je t'ai trouvé " + recherche.build().result().size() + " ami(s) dans ta ville"));
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
