package fr.leonie.jp.bot.serveur;

import fr.leonie.jp.bot.communication.Communication;

import java.util.Objects;

public class ServeurThread extends Thread {
    private final Communication communication;

    public ServeurThread(Communication communication) {
        this.communication = communication;
    }

    public void run() {
        String answer;
        String comName;


        communication.send("Bonjour comment t'appelles-tu ?");
        comName = communication.receive();
        System.out.println("Début de communication avec " + comName);

        do {
            communication.send("Que veux-tu me dire " + comName + " ?");

            // Récupération de la réponse de l'utilisateur
            answer = communication.receive();
            if (answer != null) {
                System.out.println(comName + ": " + answer);
            }
        } while(Objects.requireNonNull(answer).compareTo("a+") != 0);

        System.out.println("Fin de communication avec " + comName);
        communication.close();
    }
}
