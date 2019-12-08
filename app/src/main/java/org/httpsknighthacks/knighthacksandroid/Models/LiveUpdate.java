package org.httpsknighthacks.knighthacksandroid.Models;

import org.httpsknighthacks.knighthacksandroid.Resources.DateTimeUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class LiveUpdate {

    private String message;
    private String picture;
    private String optionalImage;
    private Timestamp timeSent;

    public LiveUpdate() { }

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

    public Timestamp getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Timestamp timeSent) {
        this.timeSent = timeSent;
    }
}
