package fr.leonie.jp.bot.communication;

import java.io.IOException;

public interface Communication {
    void open();
    void send(String msg);
    String receive() throws IOException;
    void close();
    void closeRequest(boolean wantToClose);
    boolean isCloseRequested();
}
