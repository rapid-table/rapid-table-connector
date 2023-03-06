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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rapidtable.sdk.rtc4j.exceptions.TooManyRequestException;
import com.rapidtable.sdk.rtc4j.resource.IRequest;
import com.rapidtable.sdk.rtc4j.resource.PathConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ReportCreateRequest implements IRequest {
    private static final ObjectMapper mapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .registerModule(new JavaTimeModule());

    private final String path;
    private final String body;

    ReportCreateRequest(final String path, final String body) {
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
        private String projectId = null;
        private List<ReportRequestModel> reports = new ArrayList<>();

        public ReportCreateRequest build() throws JsonProcessingException, TooManyRequestException {
            final var path = PathConfig.ROOT + PathConfig.WORKSPACE + String.format("/%s", workspaceId) +
                PathConfig.PROJECTS + String.format("/%s", projectId) + PathConfig.REPORTS;

            if (Objects.isNull(reports) || reports.isEmpty()) {
                throw new IllegalArgumentException();
            }
            if (reports.size() > 100) {
                throw new TooManyRequestException();
            }

            final var body = mapper.writeValueAsString(reports);
            return new ReportCreateRequest(path, body);
        }

        public Builder workspaceId(final String workspaceId) {
            this.workspaceId = workspaceId;
            return this;
        }

        public Builder projectId(final String projectId) {
            this.projectId = projectId;
            return this;
        }

        public Builder append(final Map<String, Object> fields) {
            this.reports.add(new ReportRequestModel(fields));
            return this;
        }

    }
}
