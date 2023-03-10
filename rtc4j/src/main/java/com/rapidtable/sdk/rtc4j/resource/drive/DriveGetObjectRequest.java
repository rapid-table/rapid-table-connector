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

import com.rapidtable.sdk.rtc4j.resource.IRequest;
import com.rapidtable.sdk.rtc4j.resource.PathConfig;

import java.util.Objects;
import java.util.regex.Matcher;

import static com.rapidtable.sdk.rtc4j.resource.PathConfig.DRIVE_OBJECT_PATH_PATTERN;
import static com.rapidtable.sdk.rtc4j.resource.PathConfig.REPORT_OBJECT_PATH_PATTERN;

public class DriveGetObjectRequest implements IRequest {
    private final String path;

    DriveGetObjectRequest(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String getQuery() {
        return null;
    }

    public String getBody() {
        return null;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String path = null;

        public DriveGetObjectRequest build() {
            if (Objects.isNull(path)) {
                throw new IllegalArgumentException();
            }
            return new DriveGetObjectRequest(path);
        }

        public Builder path(final String path) {
            Matcher matcher;

            if ((matcher = REPORT_OBJECT_PATH_PATTERN.matcher(path)).matches()) {
                final var workspaceId = matcher.group("wid");
                final var projectId = matcher.group("pid");
                final var reportId = matcher.group("rid");
                final var objectId = matcher.group("oid");
                this.path = PathConfig.ROOT + PathConfig.WORKSPACE + String.format("/%s", workspaceId) +
                    PathConfig.PROJECTS + String.format("/%s", projectId) +
                    PathConfig.REPORTS + String.format("/%s", reportId) +
                    PathConfig.OBJECTS + String.format("/%s", objectId);

            } else if ((matcher = DRIVE_OBJECT_PATH_PATTERN.matcher(path)).matches()) {
                final var workspaceId = matcher.group("wid");
                final var objectId = matcher.group("oid");

                this.path = PathConfig.ROOT + PathConfig.WORKSPACE + String.format("/%s", workspaceId) +
                    PathConfig.DRIVES + PathConfig.OBJECTS + String.format("/%s", objectId);
            }

            return this;
        }
    }
}
