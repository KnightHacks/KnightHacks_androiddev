package org.httpsknighthacks.knighthacksandroid.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class FAQ {

    private Optional<String> mQuestion;
    private Optional<String> mAnswer;

    public static final String QUESTION_KEY = "question";
    public static final String ANSWER_KEY = "answer";

    public FAQ(JSONObject jsonObject) {
        try {
            withQuestion(jsonObject.getString(QUESTION_KEY));
            withAnswer(jsonObject.getString(ANSWER_KEY));
        } catch (JSONException ex) {
            this.mQuestion = Optional.empty();
            this.mAnswer = Optional.empty();
        }
    }

    public void withQuestion(String question) {
        this.mQuestion = Optional.of(question);
    }

    public void withAnswer(String answer) {
        this.mAnswer = Optional.of(answer);
    }

    public Optional<String> getQuestionOptional() {
        return mQuestion;
    }

    public Optional<String> getAnswerOptional() {
        return mAnswer;
    }

    public static boolean isValid(FAQ faq) {
        return faq.getQuestionOptional().isPresent() && faq.getAnswerOptional().isPresent();
    }
}
