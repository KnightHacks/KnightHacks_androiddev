package org.httpsknighthacks.knighthacksandroid.Models;

import org.httpsknighthacks.knighthacksandroid.Models.Enums.ScheduleEventTypes;
import org.json.JSONException;
import org.json.JSONObject;

public class ScheduleEvent {

    private Optional<String> mTitle;
    private Optional<String> mLocation;
    private Optional<String> mStartTime;
    private Optional<String> mEndTime;
    private ScheduleEventTypes mEventType;

    public static final String TITLE_KEY = "title";
    public static final String LOCATION_KEY = "location";
    public static final String START_TIME_KEY = "startTime";
    public static final String END_TIME_KEY = "endTime";
    public static final String EVENT_TYPE_KEY = "eventType";

    public ScheduleEvent(JSONObject jsonObject) {
        try {
            withTitle(jsonObject.getString(TITLE_KEY));
            withLocation(jsonObject.getString(LOCATION_KEY));
            withStartTime(jsonObject.getString(START_TIME_KEY));
            withEndTime(jsonObject.getString(END_TIME_KEY));
            withEventType(jsonObject.getString(EVENT_TYPE_KEY));
        } catch (JSONException ex) {
            this.mTitle = Optional.empty();
            this.mStartTime = Optional.empty();
            this.mEndTime = Optional.empty();
            this.mEventType = ScheduleEventTypes.OTHER;
        }
    }

    public void withTitle(String title) {
        this.mTitle = Optional.of(title);
    }

    public void withLocation(String location) {
        this.mLocation = Optional.of(location);
    }

    public void withStartTime(String startTime) {
        this.mStartTime = Optional.of(startTime);
    }

    public void withEndTime(String endTime) {
        this.mEndTime = Optional.of(endTime);
    }

    public void withEventType(String eventType) {
        this.mEventType = ScheduleEventTypes.getScheduleEventType(eventType);
    }

    public Optional<String> getTitleOptional() {
        return mTitle;
    }

    public Optional<String> getLocationOptional() {
        return mLocation;
    }

    public Optional<String> getStartTimeOptional() {
        return mStartTime;
    }

    public Optional<String> getEndTimeOptional() {
        return mEndTime;
    }

    public ScheduleEventTypes getEventType() {
        return mEventType;
    }

    public static boolean isValid(ScheduleEvent event) {
        return event.getTitleOptional().isPresent()
                && event.getLocationOptional().isPresent()
                && event.getStartTimeOptional().isPresent()
                && event.getEndTimeOptional().isPresent();
    }
}
