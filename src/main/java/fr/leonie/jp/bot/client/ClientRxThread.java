package fr.leonie.jp.bot.client;

import fr.leonie.jp.bot.communication.Communication;

import java.io.IOException;

public class ClientRxThread implements Runnable {

    private final Communication communication;

    public ClientRxThread(Communication communication) {
        this.communication = communication;
    }

    @Override
    public void run() {
        String question = null;

        do {
            try {
                question = communication.receive();
                if(question != null) {
                    System.out.println("MeetBot: " + question);
                }
            } catch (IOException ex) {
                question = null;
            }
        } while(question != null && !communication.isCloseRequested());
        communication.closeRequest(true);
    }

}
