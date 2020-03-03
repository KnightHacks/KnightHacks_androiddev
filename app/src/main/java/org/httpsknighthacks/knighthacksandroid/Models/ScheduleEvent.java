package org.httpsknighthacks.knighthacksandroid.Models;

import java.util.Map;

public class ScheduleEvent implements Comparable<ScheduleEvent> {

    private String title;
    private String location;
    private Map<String, Object> startTime;
    private Map<String, Object> endTime;
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

    public Map<String, Object> getStartTime() {
        return startTime;
    }

    public void setStartTime(Map<String, Object> startTime) {
        this.startTime = startTime;
    }

    public Map<String, Object> getEndTime() {
        return endTime;
    }

    public void setEndTime(Map<String, Object> endTime) {
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
                && event.getEventType() != null;
    }

    @Override
    public int compareTo(ScheduleEvent o) {
        String s1 = String.valueOf(this.startTime.get("seconds"));
        String s2 = String.valueOf(o.startTime.get("seconds"));
        Long l1 = Long.parseLong(s1);
        Long l2 = Long.parseLong(s2);

        if (l1 - l2 > 0)
            return 1;

        else if (l1 - l2 < 0)
            return -1;

        return 0;
    }
}

