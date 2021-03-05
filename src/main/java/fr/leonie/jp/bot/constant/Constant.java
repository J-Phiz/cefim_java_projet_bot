package fr.leonie.jp.bot.constant;

public class Constant {
    private static final String HOSTNAME = "127.0.0.1";
    private static final int PORT = 1234;
    private static final String XML_PATH = System.getProperty("user.dir") + "/src/main/java/fr/leonie/jp/bot/";

    private static final String[] YES_ANSWERS_ARRAY = {"oui", "yes", "yep", "ouai", "ouep"};
    private static final String[] NO_ANSWERS_ARRAY = {"non", "no", "nop", "nan"};
    private static final String[] BYE_ANSWERS_ARRAY = {"a+", "revoir", "bye", "tchao", "tchuss"};
    private static final String[] OPTIONS_ARRAY = {"1", "2"};

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    private Constant() {
        // Pour interdire le constructeur
    }

    public static String getHostname() {
        return HOSTNAME;
    }

    public static int getPort() {
        return PORT;
    }

    public static String getXmlPath() {
        return XML_PATH;
    }

    public static String[] getYesAnswersArray() {
        return YES_ANSWERS_ARRAY;
    }

    public static String[] getNoAnswersArray() {
        return NO_ANSWERS_ARRAY;
    }

    public static String[] getByeAnswersArray() {
        return BYE_ANSWERS_ARRAY;
    }

    public static String[] getOptionsArray() {
        return OPTIONS_ARRAY;
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }

    public static String textInRed(String string) {
        return ANSI_RED + string + ANSI_RESET;
    }
}
