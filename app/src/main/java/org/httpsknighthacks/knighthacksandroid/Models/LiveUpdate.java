package org.httpsknighthacks.knighthacksandroid.Models;

import org.httpsknighthacks.knighthacksandroid.Resources.DateTimeUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class LiveUpdate {

    public static final String MESSAGE_KEY = "message";
    public static final String TIME_SENT_KEY = "timeSent";
    public static final String PICTURE_KEY = "picture";
    public static final String Optional_Image_key = "image";

    private String message;
    private String picture;
    private String image;
    private Timestamp timeSent;

    public LiveUpdate() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Timestamp getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Timestamp timeSent) {
        this.timeSent = timeSent;
    }

    public static boolean isValid(LiveUpdate update) {
        return update.getMessage() != null
                && update.getTimeSent() != null
                && update.getPicture() != null
                && update.getImage() != null;
    }
}
