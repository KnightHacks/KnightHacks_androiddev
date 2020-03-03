package org.httpsknighthacks.knighthacksandroid.Models;

import java.util.HashMap;
import java.util.Map;

public class Workshop implements Comparable<Workshop> {

    private String name;
    private String description;
    private String skillLevel;
    private String picture;
    private String workshopType;
    private String prerequisites;
    private Map<String, Object> startTime;
    private Map<String, Object> endTime;
    private String mapUrl;

    public Workshop() { }

    public String getWorkshopType() {
        return workshopType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setWorkshopType(String workshopType) {
        this.workshopType = workshopType;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
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

    public static boolean isValid(Workshop workshop) {
        return workshop.getName() != null
                && workshop.getDescription() != null
                && workshop.getSkillLevel() != null
                && workshop.getPicture() != null
                && workshop.getStartTime() != null
                && workshop.getEndTime() != null
                && workshop.getPrerequisites() != null
                && workshop.getWorkshopType() != null;
    }

    @Override
    public int compareTo(Workshop o) {
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

