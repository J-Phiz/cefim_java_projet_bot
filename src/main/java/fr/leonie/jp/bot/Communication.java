package fr.leonie.jp.bot;

public interface Communication {
    void open();
    void send(String msg);
    String receive();
    void close();
}
