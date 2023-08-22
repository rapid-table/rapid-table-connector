package com.rapidtable.sdk.rtc4j.resource.project.schema.datetime;

import java.time.LocalDateTime;

public record SchemaDatetimeSettings(SchemaDatetimeSubtype subtype, LocalDateTime min, LocalDateTime max,
                                     DatetimeRangeSettings range, DatetimeEvalsSettings evals) {
    public static SchemaDatetimeSettings defaultValue() {
        return new SchemaDatetimeSettings(SchemaDatetimeSubtype.Datetime,
            null, null, null, null);
    }
}