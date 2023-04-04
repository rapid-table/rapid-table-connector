/*
 * Copyright Rapid Table, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License. A copy of the License is located at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package com.rapidtable.sdk.rtc4j.resource.project;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Map;

public class SchemaField {
    private static final ObjectMapper mapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .registerModule(new JavaTimeModule());

    private String id;
    private String name;
    private String remarks;
    private SchemaFieldType type;
    private Boolean required;
    private Object settings;

    public SchemaField() {

    }

    public SchemaField(String id, String name, String remarks, SchemaFieldType type, Boolean required, Object settings) {
        this.id = id;
        this.name = name;
        this.remarks = remarks;
        this.type = type;
        this.required = required;
        this.settings = settings;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRemarks() {
        return remarks;
    }

    public SchemaFieldType getType() {
        return type;
    }

    public Boolean getRequired() {
        return required;
    }

    public Map<String, Object> getSettings() {
        return mapper.convertValue(settings, Map.class);
    }
}
