package org.httpsknighthacks.knighthacksandroid.Models;

public class ScheduleEvent {

    private String title;
    private String location;
    private Timestamp startTime;
    private Timestamp endTime;
    private String eventType;
    private String mapImage;

    public ScheduleEvent() {}

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

    public void setMapImage(String mapImage) {
        this.mapImage = mapImage;
    }

    public String getMapImage() {
        return mapImage;
    }


    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public static boolean isValid(ScheduleEvent event) {
        return event.getTitle() != null
                && event.getLocation() != null
                && event.getStartTime() != null
                && event.getEndTime() != null
                && event.getEventType() != null
                && event.getMapImage() != null;
    }
}

