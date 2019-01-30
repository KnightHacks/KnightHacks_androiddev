package org.httpsknighthacks.knighthacksandroid.Models.Enums;

import java.util.HashMap;

public enum SearchFilterTypes {
    DEV ("dev"),
    DESIGN ("design"),
    TALK ("talk"),
    WORKSHOP ("workshop"),
    FULL_TIME ("fulltime"),
    INTERNSHIP ("internship"),
    ALL ("all"),
    OTHER ("other");

    private final String searchFilterString;
    private static HashMap<String, SearchFilterTypes> lookupTable = new HashMap<>();

    static {
        for (SearchFilterTypes type: SearchFilterTypes.values()) {
            lookupTable.put(type.getSearchFilterString(), type);
        }
    }

    SearchFilterTypes(final String searchFilterString) {
        this.searchFilterString = searchFilterString;
    }

    public static SearchFilterTypes getSearchFilterType(String searchFilterString) {
        return lookupTable.containsKey(searchFilterString) ? lookupTable.get(searchFilterString) : OTHER;
    }

    public String getSearchFilterString() {
        return this.searchFilterString;
    }

    public static boolean hasTargetFilter(SearchFilterTypes types[], SearchFilterTypes target) {
        for (int i = 0; i < types.length; i++) {
            if (types[i].equals(target)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return searchFilterString;
    }
}
