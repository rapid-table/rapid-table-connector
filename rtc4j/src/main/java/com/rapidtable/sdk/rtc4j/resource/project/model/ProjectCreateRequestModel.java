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

package com.rapidtable.sdk.rtc4j.resource.project.model;

/**
 * Project Creation Request model
 */
public class ProjectCreateRequestModel {
    /**
     * Input id for the project (null if auto generation)
     */
    private String id;
    /**
     * Input name for the project (This is a required field)
     */
    private String name;
    /**
     * Input description for the project
     */
    private String description;
    /**
     * Set the project locale.
     */
    private Locales locale;

    public ProjectCreateRequestModel() {

    }

    public ProjectCreateRequestModel(String name) {
        this.name = name;
    }

    public ProjectCreateRequestModel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ProjectCreateRequestModel(String name, String description, Locales locale) {
        this.name = name;
        this.description = description;
        this.locale = locale;
    }

    public ProjectCreateRequestModel(String id, String name, String description, Locales locale) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.locale = locale;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Locales getLocale() {
        return locale;
    }

    public void setLocale(Locales locale) {
        this.locale = locale;
    }
}
