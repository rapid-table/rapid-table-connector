package com.rapidtable.sdk.rtc4j.resource.project.schema.option;

import java.util.Arrays;
import java.util.List;

public record SchemaSelectSettings(List<String> options, Boolean isRefer, String projectId,
                                   String identifyFieldId, String displayFieldId, SchemaOptionDepends dependsOn) {
    public static SchemaSelectSettings defaultValue(String... options) {
        return new SchemaSelectSettings(Arrays.stream(options).toList(), false, null,
            null, null, null);
    }
}