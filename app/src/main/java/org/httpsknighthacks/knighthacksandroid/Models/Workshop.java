package org.httpsknighthacks.knighthacksandroid.Models;


import org.httpsknighthacks.knighthacksandroid.Models.Enums.SearchFilterTypes;
import org.json.JSONException;
import org.json.JSONObject;

public class Workshop {

    private Optional<String> mName;
    private Optional<String> mDescription;
    private Optional<String> mSkillLevel;
    private Optional<String> mPicture;
    private SearchFilterTypes mWorkshopType;
    private Optional<String> mPrerequisites;
    private Optional<String> mStartTime;
    private Optional<String> mEndTime;

    public static final String NAME_KEY = "name";
    public static final String DESCRIPTION_KEY = "description";
    public static final String SKILL_LEVEL_KEY = "skillLevel";
    public static final String PICTURE_KEY = "picture";
    public static final String WORKSHOP_TYPE_KEY = "workshopType";
    public static final String PREREQUISITES_KEY = "prerequisites";
    public static final String START_TIME_KEY = "startTime";
    public static final String END_TIME_KEY = "endTime";

    public Workshop(JSONObject jsonObject) {
        try {
            withName(jsonObject.getString(NAME_KEY));
            withDescription(jsonObject.getString(DESCRIPTION_KEY));
            withSkillLevel(jsonObject.getString(SKILL_LEVEL_KEY));
            withPicture(jsonObject.getString(PICTURE_KEY));
            withWorkshopType(jsonObject.getString(WORKSHOP_TYPE_KEY));
            withPrerequisites(jsonObject.getString(PREREQUISITES_KEY));
            withStartTime(jsonObject.getString(START_TIME_KEY));
            withEndTime(jsonObject.getString(END_TIME_KEY));
        } catch (JSONException ex) {
            this.mName = Optional.empty();
            this.mSkillLevel = Optional.empty();
            this.mPicture = Optional.empty();
            this.mWorkshopType = SearchFilterTypes.ALL;
            this.mPrerequisites = Optional.empty();
            this.mStartTime = Optional.empty();
            this.mEndTime = Optional.empty();
        }
    }

    public void withName(String name) {
        this.mName = Optional.of(name);
    }

    public void withDescription(String description) {
        this.mDescription = Optional.of(description);
    }

    public void withSkillLevel(String skillLevel) {
        this.mSkillLevel = Optional.of(skillLevel);
    }

    public void withPicture(String picture) {
        this.mPicture = Optional.of(picture);
    }

    public void withWorkshopType(String workshopType) {
        this.mWorkshopType = SearchFilterTypes.getSearchFilterType(workshopType);
    }

    public void withPrerequisites(String prerequisites) {
        this.mPrerequisites = Optional.of(prerequisites);
    }

    public void withStartTime(String startTime) {
        this.mStartTime = Optional.of(startTime);
    }

    public void withEndTime(String endTime) {
        this.mEndTime = Optional.of(endTime);
    }

    public Optional<String> getNameOptional() {
        return mName;
    }

    public Optional<String> getDescriptionOptional() {
        return mDescription;
    }

    public Optional<String> getSkillLevelOptional() {
        return mSkillLevel;
    }

    public Optional<String> getPictureOptional() {
        return mPicture;
    }

    public SearchFilterTypes getWorkshopType() {
        return mWorkshopType;
    }

    public Optional<String> getPrerequisitesOptional() {
        return mPrerequisites;
    }

    public Optional<String> getStartTimeOptional() {
        return mStartTime;
    }

    public Optional<String> getEndTimeOptional() {
        return mEndTime;
    }

    public static boolean isValid(Workshop workshop) {
        return workshop.getNameOptional().isPresent()
                && workshop.getStartTimeOptional().isPresent()
                && workshop.getEndTimeOptional().isPresent();
    }
}
