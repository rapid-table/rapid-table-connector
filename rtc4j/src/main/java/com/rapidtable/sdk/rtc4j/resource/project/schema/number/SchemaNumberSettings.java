package com.rapidtable.sdk.rtc4j.resource.project.schema.number;

public record SchemaNumberSettings(Boolean negative, Boolean comma, Integer digits, String align,
                                   String min, String max, String prefixUnit, String suffixUnit,
                                   Boolean altKey) {
    public static SchemaNumberSettings defaultValue() {
        return new SchemaNumberSettings(false, false, 0, "left",
            null, null, null, null, false);
    }
}