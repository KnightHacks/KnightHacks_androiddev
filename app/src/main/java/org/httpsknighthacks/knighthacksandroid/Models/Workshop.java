package org.httpsknighthacks.knighthacksandroid.Models;
import org.httpsknighthacks.knighthacksandroid.Models.Enums.SearchFilterTypes;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Workshop {

    private Optional<String> mName;
    private Optional<String> mDescription;
    private Optional<SearchFilterTypes> mSkillLevel;
    private Optional<String> mPicture;
    private SearchFilterTypes mWorkshopType;
    private Optional<String> mPrerequisites;
    private Optional<String> mStartTime;
    private Optional<String> mEndTime;
    private Optional<String> mMapImage;

    private String name;
    private String description;
    private String skillLevel;
    private String picture;
    private String workshopType;
    private String prerequisites;
    private String mapImage;
    private Timestamp startTime;
    private Timestamp endTime;

    public Workshop() { }

    public String getWorkshopType() {
        return workshopType;
    }

    public Optional<String> getStartTimeOptional() {
        return mStartTime;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getMapEventImage() {
        return mapImage;
    }

    public void setMapEventImage(String mapImage) {
        this.mapImage = mapImage;
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

    public static boolean isValid(Workshop workshop) {
        return workshop.getName() != null
                && workshop.getDescription() != null
                && workshop.getSkillLevel() != null
                && workshop.getPicture() != null
                && workshop.getStartTime() != null
                && workshop.getEndTime() != null
                && workshop.getPrerequisites() != null
                && workshop.getWorkshopType() != null
                && workshop.getMapEventImage() != null;
    }
}

