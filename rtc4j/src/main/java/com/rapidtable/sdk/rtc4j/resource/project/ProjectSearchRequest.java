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

import com.rapidtable.sdk.rtc4j.resource.IRequest;
import com.rapidtable.sdk.rtc4j.resource.PathConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ProjectSearchRequest implements IRequest {
    private final String path;
    private final String query;

    ProjectSearchRequest(final String path, final String query) {
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
        private Integer page = null;
        private Integer size = null;
        private String asc = null;
        private String desc = null;
        private String query = null;
        private String emp = null;
        private String noemp = null;
        private List<String> includes = null;
        private List<String> in = null;

        public ProjectSearchRequest build() {
            final var path = PathConfig.ROOT + PathConfig.WORKSPACE + String.format("/%s", workspaceId) +
                PathConfig.PROJECTS + "/" + PathConfig.SEARCH;

            final var params = new ArrayList<String>();
            if (Objects.nonNull(page)) {
                params.add("page=" + page);
            }
            if (Objects.nonNull(size)) {
                params.add("size=" + size);
            }

            if (Objects.nonNull(asc)) {
                params.add("asc=" + asc);
            }
            if (Objects.nonNull(desc)) {
                params.add("desc=" + desc);
            }
            if (Objects.nonNull(query)) {
                params.add("query=" + query);
            }
            if (Objects.nonNull(emp)) {
                params.add("emp=" + emp);
            }
            if (Objects.nonNull(noemp)) {
                params.add("noemp=" + noemp);
            }
            if (Objects.nonNull(includes) && !includes.isEmpty()) {
                params.add("includes=" + String.join(";", includes));
            }
            if (Objects.nonNull(in) && !in.isEmpty()) {
                params.add("in=" + String.join(";", in));
            }

            final var query = String.join("&", params);
            return new ProjectSearchRequest(path, query);
        }

        public Builder workspaceId(final String workspaceId) {
            this.workspaceId = workspaceId;
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

        /**
         * Ascending sort field.
         * - Any
         * asc=fieldId
         * - Creation date
         * asc=cdt
         * - Update Date
         * asc=udt
         */
        public Builder asc(final String fieldId) {
            this.asc = fieldId;
            return this;
        }

        /**
         * Descending sort field.
         * - Any
         * desc=fieldId
         * - Creation date
         * desc=cdt
         * - Update Date
         * desc=udt
         */
        public Builder desc(final String fieldId) {
            this.desc = fieldId;
            return this;
        }

        /**
         * Search string.
         * query=searchText
         */
        public Builder query(final String query) {
            this.query = query;
            return this;
        }

        /**
         * Word search (blank fields only).
         * emp=fieldId
         */
        public Builder emp(final String fieldId) {
            this.emp = fieldId;
            return this;
        }

        /**
         * Word search (non-blank fields only).
         * noemp=fieldId
         */
        public Builder noemp(final String fieldId) {
            this.noemp = fieldId;
            return this;
        }

        /**
         * Exact match for specific field.
         * includes=fieldId1:searchText1;fieldId2:searchText2
         * - For example, the arguments are as follows
         * "fieldId1:searchText1", "fieldId2:searchText2",...
         */
        public Builder includes(final String... includes) {
            this.includes = Arrays.stream(includes).toList();
            return this;
        }

        /**
         * Partial match for specific field.
         * in=fieldId1:searchText1;fieldId2:searchText2
         * - For example, the arguments are as follows
         * "fieldId1:searchText1", "fieldId2:searchText2",...
         */
        public Builder in(final String... in) {
            this.in = Arrays.stream(in).toList();
            return this;
        }

    }
}
