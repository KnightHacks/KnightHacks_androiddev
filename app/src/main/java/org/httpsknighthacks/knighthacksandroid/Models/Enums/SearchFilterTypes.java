package org.httpsknighthacks.knighthacksandroid.Models.Enums;

import java.util.HashMap;
import java.util.List;

public enum SearchFilterTypes {
    DEV ("Development"),
    DESIGN ("Design"),
    TALK ("Talk"),
    HARDWARE ("Hardware"),
    CAREER ("Career"),
    WORKSHOP ("Workshop"),
    FULL_TIME ("Full Time"),
    INTERNSHIP ("Internships"),
    ALL ("All"),
    MAIN_EVENTS ("Main Event"),
    FOOD ("Food"),
    BEGINNER ("Beginner"),
    ADVANCED ("Advanced");

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
        return lookupTable.containsKey(searchFilterString) ? lookupTable.get(searchFilterString) : ALL;
    }

    public String getSearchFilterString() {
        return this.searchFilterString;
    }

    public static boolean hasTargetFilter(List<String> types, SearchFilterTypes target) {
        for (int i = 0; i < types.size(); i++) {
            if (types.get(i).equals(target)) {
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
