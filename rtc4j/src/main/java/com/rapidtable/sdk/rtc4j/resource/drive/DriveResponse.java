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

package com.rapidtable.sdk.rtc4j.resource.drive;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Drive Resource Response model
 */
public class DriveResponse {
    /**
     * Drive asset ID
     */
    private String id;
    /**
     * Drive asset path
     */
    private String path;
    /**
     * Drive asset size
     */
    private Long size;
    /**
     * Tag ID assigned to the asset
     */
    private List<String> tagIds;
    /**
     * Drive asset update date
     */
    private LocalDateTime date;
    /**
     * Drive asset content type
     */
    private String contentType;
    /**
     * Drive asset name
     */
    private String name;

    public DriveResponse() {

    }

    public DriveResponse(String id, String path, Long size, List<String> tagIds, LocalDateTime date, String contentType, String name) {
        this.id = id;
        this.path = path;
        this.size = size;
        this.tagIds = tagIds;
        this.date = date;
        this.contentType = contentType;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public List<String> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<String> tagIds) {
        this.tagIds = tagIds;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
