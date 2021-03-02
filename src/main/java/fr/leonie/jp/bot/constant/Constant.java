package fr.leonie.jp.bot.constant;

public class Constant {
    private static final String hostname = "127.0.0.1";
    private static final int port = 1234;

    private Constant() {
        // Pour interdire le constructeur
    }

    public static String getHostname() {
        return hostname;
    }

    public static int getPort() {
        return port;
    }
}
