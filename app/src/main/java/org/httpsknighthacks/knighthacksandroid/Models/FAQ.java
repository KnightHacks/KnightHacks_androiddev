package org.httpsknighthacks.knighthacksandroid.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class FAQ {
    private String question;
    private String answer;

    public static final String QUESTION_KEY = "question";
    public static final String ANSWER_KEY = "answer";

    public FAQ() {}

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public static boolean isValid(FAQ faq) {
        return faq.getQuestion() != null && faq.getAnswer() != null;
    }
}
