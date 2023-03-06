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

import com.rapidtable.sdk.rtc4j.resource.IDeleteRequest;
import com.rapidtable.sdk.rtc4j.resource.PathConfig;

import java.io.IOException;
import java.util.Objects;

import static com.rapidtable.sdk.rtc4j.resource.PathConfig.OBJECT_PATH_PATTERN;

public class ReportDeleteObjectRequest implements IDeleteRequest {
    private final String path;

    ReportDeleteObjectRequest(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String getQuery() {
        return null;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String workspaceId = null;
        private String projectId = null;
        private String reportId = null;
        private String objectId = null;
        private String target = null;

        public ReportDeleteObjectRequest build() throws IOException {
            if (Objects.nonNull(target)) {
                final var matcher = OBJECT_PATH_PATTERN.matcher(target);
                if (!matcher.matches()) {
                    throw new IllegalArgumentException();
                }
                final var workspaceId = matcher.group("wid");
                final var projectId = matcher.group("pid");
                final var reportId = matcher.group("rid");
                final var objectId = matcher.group("oid");
                final var path = PathConfig.ROOT + PathConfig.WORKSPACE + String.format("/%s", workspaceId) +
                    PathConfig.PROJECTS + String.format("/%s", projectId) +
                    PathConfig.REPORTS + String.format("/%s", reportId) +
                    PathConfig.OBJECTS + String.format("/%s", objectId);

                return new ReportDeleteObjectRequest(path);
            } else {
                final var path = PathConfig.ROOT + PathConfig.WORKSPACE + String.format("/%s", workspaceId) +
                    PathConfig.PROJECTS + String.format("/%s", projectId) +
                    PathConfig.REPORTS + String.format("/%s", reportId) +
                    PathConfig.OBJECTS + String.format("/%s", objectId);

                return new ReportDeleteObjectRequest(path);
            }
        }

        public Builder workspaceId(final String workspaceId) {
            this.workspaceId = workspaceId;
            return this;
        }

        public Builder projectId(final String projectId) {
            this.projectId = projectId;
            return this;
        }

        public Builder reportId(final String reportId) {
            this.reportId = reportId;
            return this;
        }

        public Builder objectId(final String objectId) {
            this.objectId = objectId;
            return this;
        }

        public Builder target(final String target) {
            this.target = target;
            return this;
        }
    }
}
