package com.rapidtable.sdk.rtc4j.resource.project.schema.option;

import java.util.Arrays;
import java.util.List;

public record SchemaRadioSettings(List<String> options, Boolean isRefer, String projectId,
                                  String identifyFieldId, String displayFieldId, SchemaOptionDepends dependsOn) {
    public static SchemaRadioSettings defaultValue(String... options) {
        return new SchemaRadioSettings(Arrays.stream(options).toList(), false, null,
            null, null, null);
    }
}