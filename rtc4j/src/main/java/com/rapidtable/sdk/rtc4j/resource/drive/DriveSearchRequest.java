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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DriveSearchRequest implements IRequest {
    private final String path;
    private final String query;

    DriveSearchRequest(final String path, final String query) {
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
        private DriveComponentType componentType = null;
        private Integer page = null;
        private Integer size = null;
        private String query = null;
        private List<String> tagIds = null;

        public DriveSearchRequest build() {
            final var path = PathConfig.ROOT + PathConfig.WORKSPACE + String.format("/%s", workspaceId) +
                PathConfig.DRIVES + PathConfig.SEARCH;
            final var params = new ArrayList<String>();
            if (Objects.nonNull(page)) {
                params.add("page=" + page);
            }
            if (Objects.nonNull(size)) {
                params.add("size=" + size);
            }
            if (Objects.nonNull(componentType)) {
                params.add("component=" + componentType.getKey());
            }
            if (Objects.nonNull(query)) {
                params.add("query=" + query);
            }
            if (Objects.nonNull(tagIds) && !tagIds.isEmpty()) {
                params.add("tagIds=" + String.join(",", tagIds));
            }
            final var query = String.join("&", params);
            return new DriveSearchRequest(path, query);
        }

        public Builder workspaceId(final String workspaceId) {
            this.workspaceId = workspaceId;
            return this;
        }

        public Builder componentType(final DriveComponentType componentType) {
            this.componentType = componentType;
            return this;
        }

        public Builder page(final Integer page) {
            this.page = page;
            return this;
        }

        public Builder size(final Integer size) {
            this.size = size;
            return this;
        }

        public Builder query(final String query) {
            this.query = query;
            return this;
        }

        public Builder tagIds(final List<String> tagIds) {
            this.tagIds = tagIds;
            return this;
        }
    }
}
