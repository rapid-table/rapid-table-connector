package com.rapidtable.sdk.rtc4j.resource.project.schema.text;

import java.util.List;

public record SchemaTextSettings(SchemaTextSubtype subtype,
                                 Integer minLength, Integer maxLength, Boolean altKey,
                                 List<String> validators) {
    public static SchemaTextSettings defaultValue() {
        return new SchemaTextSettings(SchemaTextSubtype.Keyword,
            null, null, false, List.of());
    }
}