package fr.leonie.jp.bot.client;

import fr.leonie.jp.bot.communication.ClientCommunication;
import fr.leonie.jp.bot.communication.Communication;

import java.util.Scanner;

public class ClientMain {

    private final Communication communication;


    public ClientMain() {
       communication = new ClientCommunication();
    }


    public void run() {

        Thread txCommunication = new Thread(new ClientTxThread(communication));
        Thread rxCommunication = new Thread(new ClientRxThread(communication));

        System.out.println();
        System.out.println();

        communication.open();

        txCommunication.start();
        rxCommunication.start();

        try {
            txCommunication.join();
            rxCommunication.join();
        } catch (InterruptedException ex) {
            System.out.println("Interrupt erreur: " + ex.getMessage());
            ex.printStackTrace();
        }

        communication.close();
    }

}
