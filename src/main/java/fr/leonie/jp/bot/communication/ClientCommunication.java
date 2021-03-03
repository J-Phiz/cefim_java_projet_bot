package fr.leonie.jp.bot.communication;

import fr.leonie.jp.bot.communication.Communication;
import fr.leonie.jp.bot.constant.Constant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientCommunication implements Communication {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean wantToClose;


    @Override
    public void open() {
        try {
            // Ouverture de la com avec le serveur
            socket = new Socket(Constant.getHostname(), Constant.getPort());
            // Flux pour envoyer
            out = new PrintWriter(socket.getOutputStream());
            // Flux pour recevoir
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (UnknownHostException ex) {
            System.out.println("Serveur non trouvé: " + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("I/O erreur: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void send(String msg) {
        out.println(msg);
        out.flush();
    }

    @Override
    public String receive() {
        String msg = null;

        try {
            msg = in.readLine();
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
            ex.printStackTrace();
        }

        return msg;
    }

    @Override
    public void close() {
        out.close();
        try {
            socket.close();
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void closeRequest(boolean wantToClose) {
        this.wantToClose = wantToClose;

    }

    @Override
    public boolean isCloseRequested() {
        return wantToClose;
    }

}
