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

    private String name;
    private String description;
    private String skillLevel;
    private String picture;
    private String workshopType;
    private String prerequisites;
    private Timestamp startTime;
    private Timestamp endTime;

    public static final String NAME_KEY = "name";
    public static final String DESCRIPTION_KEY = "description";
    public static final String SKILL_LEVEL_KEY = "skillLevel";
    public static final String PICTURE_KEY = "picture";
    public static final String WORKSHOP_TYPE_KEY = "workshopType";
    public static final String PREREQUISITES_KEY = "prerequisites";
    public static final String START_TIME_KEY = "startTime";
    public static final String END_TIME_KEY = "endTime";

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
}
