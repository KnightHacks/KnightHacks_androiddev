package org.httpsknighthacks.knighthacksandroid.Models;

import org.httpsknighthacks.knighthacksandroid.Models.Enums.SearchFilterTypes;

public class ScheduleEvent {

    private Optional<String> mName;
    private Optional<String> mDescription;
    private Optional<SearchFilterTypes> mSkillLevel;
    private Optional<String> mPicture;
    private SearchFilterTypes mWorkshopType;
    private Optional<String> mPrerequisites;
    private Optional<String> mStartTime;
    private Optional<String> mEndTime;

    private String title;
    private String location;
    private Timestamp startTime;
    private Timestamp endTime;
    private String eventType;

    public ScheduleEvent() {}

    public Optional<String> getStartTimeOptional() {
        return mStartTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getEventType() {
        return eventType;
    }


    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public static boolean isValid(ScheduleEvent event) {
        return event.getTitle() != null
                && event.getLocation() != null
                && event.getStartTime() != null
                && event.getEndTime() != null
                && event.getEventType() != null;
    }
}

