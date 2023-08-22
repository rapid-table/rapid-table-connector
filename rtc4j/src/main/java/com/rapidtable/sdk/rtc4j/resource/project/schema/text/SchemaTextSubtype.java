package com.rapidtable.sdk.rtc4j.resource.project.schema.text;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SchemaTextSubtype {
    Keyword(1),
    Longtext(2),
    Richtext(3);
    private final int code;

    SchemaTextSubtype(int code) {
        this.code = code;
    }

    public boolean same(final int subtype) {
        return subtype == code;
    }

    @JsonValue
    public int getCode() {
        return this.code;
    }

    @JsonCreator
    public static SchemaTextSubtype of(final int key) {
        for (final SchemaTextSubtype value : SchemaTextSubtype.values()) {
            if (key == value.code) {
                return value;
            }
        }
        return null;
    }
}
