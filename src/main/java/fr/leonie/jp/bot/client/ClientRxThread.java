package fr.leonie.jp.bot.client;

import fr.leonie.jp.bot.communication.Communication;

public class ClientRxThread implements Runnable {

    private final Communication communication;

    public ClientRxThread(Communication communication) {
        this.communication = communication;
    }

    @Override
    public void run() {
        String question;

        do {
            question = communication.receive();
            if(question != null) {
                System.out.println("MeetBot: " + question);
            }
        } while(question != null);

    }
}
