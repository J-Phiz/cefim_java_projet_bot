package fr.leonie.jp.bot.communication;

import fr.leonie.jp.bot.constant.Constant;

import java.util.Arrays;
import java.util.List;

public class ToolsCommunication {

    private static final List<String> byeAnswers = Arrays.asList(Constant.getByeAnswersArray());

    public static boolean isEndCommunication(String msg) {
        String[] words = msg.toLowerCase().split(" ");
        for(String word : words) {
            if(byeAnswers.contains(word)){
                return true;
            }
        }
        return false;
    }
}
