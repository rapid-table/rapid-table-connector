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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rapidtable.sdk.rtc4j.exceptions.TooManyRequestException;
import com.rapidtable.sdk.rtc4j.resource.IRequest;
import com.rapidtable.sdk.rtc4j.resource.PathConfig;
import com.rapidtable.sdk.rtc4j.resource.project.model.ProjectCreateRequestModel;

import java.util.Objects;

public class ProjectCreateRequest implements IRequest {
    private static final ObjectMapper mapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .registerModule(new JavaTimeModule());

    private final String path;
    private final String body;

    ProjectCreateRequest(final String path, final String body) {
        this.path = path;
        this.body = body;
    }

    public String getPath() {
        return path;
    }

    public String getQuery() {
        return null;
    }

    public String getBody() {
        return body;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String workspaceId = null;
        private ProjectCreateRequestModel model = null;

        public ProjectCreateRequest build() throws JsonProcessingException, TooManyRequestException {
            final var path = PathConfig.ROOT + PathConfig.WORKSPACE + String.format("/%s", workspaceId) + PathConfig.PROJECTS;

            if (Objects.isNull(model)) {
                throw new IllegalArgumentException();
            }

            final var body = mapper.writeValueAsString(model);
            return new ProjectCreateRequest(path, body);
        }

        public Builder workspaceId(final String workspaceId) {
            this.workspaceId = workspaceId;
            return this;
        }

        public Builder model(final ProjectCreateRequestModel model) {
            this.model = model;
            return this;
        }
    }
}
