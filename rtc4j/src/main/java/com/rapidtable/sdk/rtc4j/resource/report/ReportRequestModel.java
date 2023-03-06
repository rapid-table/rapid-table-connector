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

import java.util.Map;

/**
 * Report Resource Request model
 */
public class ReportRequestModel {
    /**
     * Input form id for the report (null if there is no specific layout)
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

    public ReportRequestModel() {

    }

    public ReportRequestModel(Map<String, Object> fields) {
        this.fields = fields;
    }

    public ReportRequestModel(String id, Map<String, Object> fields) {
        this.id = id;
        this.fields = fields;
    }

    public ReportRequestModel(String id, String formId, Map<String, Object> fields) {
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

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

}
