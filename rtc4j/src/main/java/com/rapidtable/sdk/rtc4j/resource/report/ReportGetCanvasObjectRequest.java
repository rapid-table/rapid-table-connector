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

import com.rapidtable.sdk.rtc4j.resource.IRequest;
import com.rapidtable.sdk.rtc4j.resource.PathConfig;

import java.util.Objects;

import static com.rapidtable.sdk.rtc4j.resource.PathConfig.REPORT_CANVAS_OBJECT_PATH_PATTERN;

public class ReportGetCanvasObjectRequest implements IRequest {
    private final String path;
    private final String query;

    ReportGetCanvasObjectRequest(final String path, final String query) {
        this.path = path;
        this.query = query;
    }

    public String getPath() {
        return path;
    }

    public String getQuery() {
        return query;
    }

    public String getBody() {
        return null;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String path = null;

        public ReportGetCanvasObjectRequest build() {
            return new ReportGetCanvasObjectRequest(path, null);
        }

        public Builder target(final Object value) {
            if (Objects.nonNull(value) && value instanceof String path) {
                final var matcher = REPORT_CANVAS_OBJECT_PATH_PATTERN.matcher(path);
                if (matcher.matches()) {
                    final var workspaceId = matcher.group("wid");
                    final var projectId = matcher.group("pid");
                    final var reportId = matcher.group("rid");
                    final var fieldId = matcher.group("fid");
                    this.path = PathConfig.ROOT + PathConfig.WORKSPACE + String.format("/%s", workspaceId) +
                        PathConfig.PROJECTS + String.format("/%s", projectId) +
                        PathConfig.REPORTS + String.format("/%s", reportId) +
                        PathConfig.CANVAS + String.format("/%s", fieldId);
                }
            }
            return this;
        }

    }
}
