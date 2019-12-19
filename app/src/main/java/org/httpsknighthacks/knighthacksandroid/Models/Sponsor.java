package org.httpsknighthacks.knighthacksandroid.Models;

import org.httpsknighthacks.knighthacksandroid.Models.Enums.SearchFilterTypes;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Sponsor {

    private Optional<String> mName;
    private Optional<String> mLocation;
    private Optional<ArrayList[]> mOfferings;
    private Optional<String> mDescription;
    private Optional<String> mPicture;

    private String name;
    private String location;
    private String description;
    private String picture;
    private String offerings;

    public static final String NAME_KEY = "name";
    public static final String LOCATION_KEY = "location";
    public static final String OFFERINGS_KEY = "offerings";
    public static final String DESCRIPTION_KEY = "description";
    public static final String PICTURE_KEY = "picture";

    public Sponsor() {

    }

    public Optional<String> getNameOptional() {
        return mName;
    }

    public Optional<String> getLocationOptional() {
        return mLocation;
    }

    public Optional<ArrayList[]> getOfferingsOptional() {
        return mOfferings;
    }

    public Optional<String> getDescriptionOptional() {
        return mDescription;
    }

    public Optional<String> getPictureOptional() {
        return mPicture;
    }

    public String getOfferings() {
        String fulltime = getFullTime();
        String internship = getInternship();

        if (fulltime == null && internship == null) {
            return null;
        }

        if (fulltime == null) {
            return internship;
        }

        if (internship == null) {
            return fulltime;
        }

        return internship + ", " + fulltime;
    }

    public String getInternship() {

        return "Internship";
    }

    public String getFullTime() {
        /*
        if (SearchFilterTypes.hasTargetFilter(offerings, SearchFilterTypes.FULL_TIME)) {
            return SearchFilterTypes.FULL_TIME.toString();
        }
        */

        return "Full-time";
    }

    public static boolean isValid(Sponsor sponsor) {
        return sponsor.getName() != null
                && sponsor.getLocation() != null
                && sponsor.getDescription() != null
                && sponsor.getPicture() != null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setOfferings(String offerings) {
        this.offerings = offerings;
    }
}
