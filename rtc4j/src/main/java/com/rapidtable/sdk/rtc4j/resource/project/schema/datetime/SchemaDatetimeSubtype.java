package com.rapidtable.sdk.rtc4j.resource.project.schema.datetime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SchemaDatetimeSubtype {
    Datetime(1),
    Date(2),
    Time(3);
    private final int code;

    SchemaDatetimeSubtype(int code) {
        this.code = code;
    }

    @JsonValue
    public int getCode() {
        return this.code;
    }

    @JsonCreator
    public static SchemaDatetimeSubtype of(final int key) {
        for (final SchemaDatetimeSubtype value : SchemaDatetimeSubtype.values()) {
            if (key == value.code) {
                return value;
            }
        }
        return null;
    }
}
