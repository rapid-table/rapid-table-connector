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

public class DriveGetObjectRequest implements IRequest {
    private final String path;
    private final String query;

    DriveGetObjectRequest(final String path, final String query) {
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
        private String workspaceId = null;
        private String objectId = null;

        public DriveGetObjectRequest build() {
            final var path = PathConfig.ROOT + PathConfig.WORKSPACE + String.format("/%s", workspaceId) +
                PathConfig.DRIVES + PathConfig.OBJECTS + String.format("/%s", objectId);
            return new DriveGetObjectRequest(path, null);
        }

        public Builder workspaceId(final String workspaceId) {
            this.workspaceId = workspaceId;
            return this;
        }

        public Builder objectId(final String objectId) {
            this.objectId = objectId;
            return this;
        }
    }
}
