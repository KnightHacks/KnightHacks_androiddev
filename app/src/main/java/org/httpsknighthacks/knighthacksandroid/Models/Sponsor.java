package org.httpsknighthacks.knighthacksandroid.Models;

import org.httpsknighthacks.knighthacksandroid.Models.Enums.SearchFilterTypes;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Sponsor {

    private Optional<String> mName;
    private Optional<String> mLocation;
    private Optional<SearchFilterTypes[]> mOfferings;
    private Optional<String> mDescription;
    private Optional<String> mPicture;

    public static final String NAME_KEY = "name";
    public static final String LOCATION_KEY = "location";
    public static final String OFFERINGS_KEY = "offerings";
    public static final String DESCRIPTION_KEY = "description";
    public static final String PICTURE_KEY = "picture";

    public Sponsor(JSONObject jsonObject) {
        try {
            withName(jsonObject.getString(NAME_KEY));
            withLocation(jsonObject.getString(LOCATION_KEY));
            withOfferrings(jsonObject.getJSONArray(OFFERINGS_KEY));
            withDescription(jsonObject.getString(DESCRIPTION_KEY));
            withPicture(jsonObject.getString(PICTURE_KEY));
        } catch (JSONException ex) {
            this.mName = Optional.empty();
            this.mLocation = Optional.empty();
            this.mOfferings = Optional.empty();
            this.mDescription = Optional.empty();
            this.mPicture = Optional.empty();
        }
    }

    public void withName(String mName) {
        this.mName = Optional.of(mName);
    }

    public void withLocation(String mLocation) {
        this.mLocation = Optional.of(mLocation);
    }

    public void withOfferrings(JSONArray mOfferings) {
        int numOfferings = mOfferings.length();
        SearchFilterTypes[] offerings = new SearchFilterTypes[numOfferings];

        try {
            for (int i = 0; i < numOfferings; i++) {
                offerings[i] = SearchFilterTypes.getSearchFilterType(mOfferings.getString(i));
            }
        } catch (JSONException ex) {
            offerings = new SearchFilterTypes[numOfferings];
        }

        this.mOfferings = Optional.of(offerings);
    }

    public void withDescription(String mDescription) {
        this.mDescription = Optional.of(mDescription);
    }

    public void withPicture(String mPicture) {
        this.mPicture = Optional.of(mPicture);
    }

    public Optional<String> getNameOptional() {
        return mName;
    }

    public Optional<String> getLocationOptional() {
        return mLocation;
    }

    public Optional<SearchFilterTypes[]> getOfferringsOptional() {
        return mOfferings;
    }

    public Optional<String> getDescriptionOptional() {
        return mDescription;
    }

    public Optional<String> getPictureOptional() {
        return mPicture;
    }

    public static boolean isValid(Sponsor sponsor) {
        return sponsor.getNameOptional().isPresent()
                && sponsor.getDescriptionOptional().isPresent()
                && sponsor.getPictureOptional().isPresent();
    }
}
