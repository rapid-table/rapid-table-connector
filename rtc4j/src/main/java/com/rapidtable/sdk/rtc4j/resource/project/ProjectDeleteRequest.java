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
import com.rapidtable.sdk.rtc4j.exceptions.TooManyRequestException;
import com.rapidtable.sdk.rtc4j.resource.IDeleteRequest;
import com.rapidtable.sdk.rtc4j.resource.PathConfig;

public class ProjectDeleteRequest implements IDeleteRequest {
    private final String path;
    private final String query;

    ProjectDeleteRequest(final String path, final String query) {
        this.path = path;
        this.query = query;
    }

    public String getPath() {
        return path;
    }

    public String getQuery() {
        return query;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String workspaceId = null;
        private String projectId = null;
        private Boolean exterminate = false;

        public ProjectDeleteRequest build() throws JsonProcessingException, TooManyRequestException {
            final var path = PathConfig.ROOT + PathConfig.WORKSPACE + String.format("/%s", workspaceId) +
                PathConfig.PROJECTS + String.format("/%s", projectId);

            final var query = exterminate ? "exterminate=true" : "";
            return new ProjectDeleteRequest(path, query);
        }

        public Builder workspaceId(final String workspaceId) {
            this.workspaceId = workspaceId;
            return this;
        }

        public Builder projectId(final String projectId) {
            this.projectId = projectId;
            return this;
        }

        public Builder exterminate(final Boolean exterminate) {
            this.exterminate = exterminate;
            return this;
        }
    }
}
