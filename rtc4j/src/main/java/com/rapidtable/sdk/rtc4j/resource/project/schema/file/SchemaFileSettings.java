package com.rapidtable.sdk.rtc4j.resource.project.schema.file;

import java.util.List;

public record SchemaFileSettings(List<String> accepts, Integer maxSize, Boolean multiple, Boolean useDrive) {
    public static final Integer DEFAULT_MAX_VALUE = 1024 * 1024 * 10;
}
