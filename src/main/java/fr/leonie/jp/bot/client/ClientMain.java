package fr.leonie.jp.bot.client;

import fr.leonie.jp.bot.Communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {

    private final Communication communication;


    public ClientMain() {
       communication = new ClientCommunication();
    }


    public void run() {
        final Scanner sc = new Scanner(System.in);
        String question;
        String answer;

        communication.open();

        do {
            // Récupération de la question du bot
            question = communication.receive();
            if (question != null) {
                System.out.println(question);

                // Réponse de l'utilisateur et envoi au bot
                answer = sc.nextLine();
                communication.send(answer);
            }
        } while(question != null);

        communication.close();
    }

}
