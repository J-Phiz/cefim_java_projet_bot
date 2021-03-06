package fr.leonie.jp.bot.client;

import fr.leonie.jp.bot.communication.Communication;
import fr.leonie.jp.bot.communication.ToolsCommunication;
import fr.leonie.jp.bot.constant.Constant;

import java.util.*;

public class ClientTxThread implements Runnable {

    private final Communication communication;

    public ClientTxThread(Communication communication) {
        this.communication = communication;
    }

    @Override
    public void run() {

        final Scanner sc = new Scanner(System.in);
        String answer;
        boolean fin = false;

        do {
            answer = sc.nextLine();
            if(answer != null) {
                communication.send(answer);
                fin = ToolsCommunication.isEndCommunication(answer);
            }
        } while(!fin && !communication.isCloseRequested());
        communication.closeRequest(true);
    }
}
