package org.httpsknighthacks.knighthacksandroid.Models.Enums;

import java.util.HashMap;

public enum ScheduleEventTypes {
    DEV ("dev"),
    DESIGN ("design"),
    TALK ("talk"),
    WORKSHOP ("workshop"),
    ALL ("all"),
    OTHER ("other");

    private final String scheduleEventString;
    private static HashMap<String, ScheduleEventTypes> lookupTable = new HashMap<>();

    static {
        for (ScheduleEventTypes type: ScheduleEventTypes.values()) {
            lookupTable.put(type.getScheduleEventString(), type);
        }
    }

    ScheduleEventTypes(final String scheduleEventString) {
        this.scheduleEventString = scheduleEventString;
    }

    public static ScheduleEventTypes getScheduleEventType(String scheduleEventString) {
        return lookupTable.containsKey(scheduleEventString) ? lookupTable.get(scheduleEventString) : OTHER;
    }

    public String getScheduleEventString() {
        return this.scheduleEventString;
    }

    @Override
    public String toString() {
        return scheduleEventString;
    }
}
