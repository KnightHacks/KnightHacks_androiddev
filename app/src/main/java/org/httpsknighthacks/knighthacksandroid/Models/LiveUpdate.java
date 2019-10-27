package org.httpsknighthacks.knighthacksandroid.Models;

import org.httpsknighthacks.knighthacksandroid.Resources.DateTimeUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class LiveUpdate {

    private Optional<String> mMessage;
    private Optional<String> mTimeSent;
    private Optional<String> mPicture;
    private Optional<String> mOptionalImage;

    public static final String MESSAGE_KEY = "message";
    public static final String TIME_SENT_KEY = "timeSent";
    public static final String PICTURE_KEY = "picture";
    public static final String OPTIONAL_IMAGE_KEY = "image";

    public LiveUpdate(JSONObject jsonObject) {
        try {
            withMessage(jsonObject.getString(MESSAGE_KEY));
            withTimeSent(DateTimeUtils.getTimeAndDurationSinceGiven(jsonObject.getString(TIME_SENT_KEY)));
            withPicture(jsonObject.getString(PICTURE_KEY));
            withOptionalImage(jsonObject.getString(OPTIONAL_IMAGE_KEY));
        } catch (JSONException ex) {
            this.mMessage = Optional.empty();
            this.mTimeSent = Optional.empty();
            this.mPicture = Optional.empty();
            this.mOptionalImage = Optional.empty();
        }
    }

    public void withMessage(String message) {
        this.mMessage = Optional.of(message);
    }

    public void withTimeSent(String timeSent) {
        this.mTimeSent = Optional.of(timeSent);
    }

    public void withPicture(String picture) {
        this.mPicture = Optional.of(picture);
    }

    public void withOptionalImage(String imageView) {
        this.mOptionalImage = Optional.of(imageView);
    }

    public Optional<String> getMessageOptional() {
        return mMessage;
    }

    public Optional<String> getTimeSentOptional() {
        return mTimeSent;
    }

    public Optional<String> getPictureOptional() {
        return mPicture;
    }

    public Optional<String> getImageOptional () {
        return mOptionalImage;
    }

    public static boolean isValid(LiveUpdate update) {
        return update.getMessageOptional().isPresent()
                && update.getTimeSentOptional().isPresent()
                && update.getPictureOptional().isPresent()
                && update.getImageOptional().isPresent();
    }
}
