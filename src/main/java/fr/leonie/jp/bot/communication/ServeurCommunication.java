package fr.leonie.jp.bot.communication;

import fr.leonie.jp.bot.communication.Communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurCommunication implements Communication {

    private final ServerSocket serverSocket;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean wantToClose;


    public ServeurCommunication(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void open() {
        try {
            socket = serverSocket.accept();
            // Flux pour envoyer
            out = new PrintWriter(socket.getOutputStream());
            // Flux pour recevoir
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
    public String receive() throws IOException {
        String msg;

        msg = in.readLine();
        if (msg != null && ToolsCommunication.isEndCommunication(msg)) {
            throw new IOException();
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
