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

package com.rapidtable.sdk.rtc4j.resource.report;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Map;
import java.util.Objects;

/**
 * Report Resource Response model
 */
public class ReportResponse {
    private static final ObjectMapper mapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .registerModule(new JavaTimeModule());

    /**
     * Report ID
     */
    private String id;
    /**
     * Input form id for the report (null if there is no specific layout)
     */
    private String formId;
    /**
     * Returns each field as an associative array (key ... field id, value ... input value)
     */
    private Map<String, Object> fields;

    public ReportResponse() {

    }

    public ReportResponse(String id, String formId, Map<String, Object> fields) {
        this.id = id;
        this.formId = formId;
        this.fields = fields;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public <T> T getField(final String key, final Object defaultValue,
                          final Class<T> classType) {
        final var value = fields.getOrDefault(key, defaultValue);
        if (Objects.nonNull(value)) {
            return mapper.convertValue(value, classType);
        }
        return null;
    }

    public <T> T getField(final String key, final Class<T> classType) {
        return getField(key, null, classType);
    }

    public void setField(final String key, final Object value) {
        this.fields.put(key, value);
    }

    public void setFields(final Map<String, Object> fields) {
        this.fields = fields;
    }

}
