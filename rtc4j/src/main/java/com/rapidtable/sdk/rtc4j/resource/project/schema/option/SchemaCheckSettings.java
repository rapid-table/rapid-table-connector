package com.rapidtable.sdk.rtc4j.resource.project.schema.option;

import java.util.Arrays;
import java.util.List;

public record SchemaCheckSettings(List<String> options, Boolean isRefer, String projectId,
                                  String identifyFieldId, String displayFieldId, SchemaOptionDepends dependsOn,
                                  Boolean multiple) {
    public static SchemaCheckSettings defaultValue(String... options) {
        return new SchemaCheckSettings(Arrays.stream(options).toList(), false, null,
            null, null, null, true);
    }
}