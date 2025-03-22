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

import com.rapidtable.sdk.rtc4j.exceptions.TooManyRequestException;
import com.rapidtable.sdk.rtc4j.resource.IRequest;
import com.rapidtable.sdk.rtc4j.resource.PathConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ReportAggregateValueRequest implements IRequest {
    private final String path;
    private final String query;

    ReportAggregateValueRequest(final String path, final String query) {
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

    public static class Builder extends ReportSearchBuilderBase<ReportAggregateValueRequest> {
        private List<String> projectIds = new ArrayList<>();
        private String fieldId = null;

        public ReportAggregateValueRequest build() throws TooManyRequestException {
            final var path = PathConfig.ROOT + PathConfig.WORKSPACE + String.format("/%s", workspaceId) +
                PathConfig.PROJECTS + PathConfig.REPORTS + PathConfig.AGGREGATE;

            if (projectIds.isEmpty()) {
                throw new IllegalArgumentException();
            }
            if (Objects.isNull(fieldId)) {
                throw new IllegalArgumentException();
            }
            if (projectIds.size() > 300) {
                throw new TooManyRequestException();
            }

            final var params = getParams();
            params.add("projectIds=" + String.join(",", projectIds));
            params.add("fieldId=" + fieldId);

            final var query = String.join("&", params);
            return new ReportAggregateValueRequest(path, query);
        }

        public Builder workspaceId(final String workspaceId) {
            this.workspaceId = workspaceId;
            return this;
        }

        public Builder projectId(final String projectId) {
            this.projectIds = new ArrayList<>();
            this.projectIds.add(projectId);
            return this;
        }

        /**
         * Write the project IDs separated by commas.
         * ids=project-id1,project-id2,project-id3
         */
        public Builder projectIds(final String... ids) {
            this.projectIds.addAll(Arrays.asList(ids));
            return this;
        }

        /**
         * Field ID to be aggregated.
         * fieldId=fieldId
         */
        public Builder fieldId(final String fieldId) {
            this.fieldId = fieldId;
            return this;
        }

        public Builder page(final Integer page) {
            // Disabled method
            return this;
        }

        public Builder size(final Integer size) {
            // Disabled method
            return this;
        }

        public Builder asc(final String fieldId) {
            // Disabled method
            return this;
        }

        public Builder desc(final String fieldId) {
            // Disabled method
            return this;
        }

        public Builder query(final String query) {
            super.query(query);
            return this;
        }

        /**
         * Word search (blank fields only).
         * emp=fieldId
         */
        public Builder emp(final String fieldId) {
            super.emp(fieldId);
            return this;
        }

        /**
         * Word search (non-blank fields only).
         * noemp=fieldId
         */
        public Builder noemp(final String fieldId) {
            super.noemp(fieldId);
            return this;
        }

        /**
         * Word search (specific keyword).
         * includes=fieldId1:searchText1;fieldId2:searchText2
         * - For example, the arguments are as follows
         * "fieldId1:searchText1", "fieldId2:searchText2",...
         */
        public Builder includes(final String... includes) {
            super.includes(includes);
            return this;
        }

        /**
         * Numeric value (equals).
         * eq=fieldId:123
         */
        public Builder eq(final String fieldId, final String value) {
            super.eq(fieldId, value);
            return this;
        }

        /**
         * Numeric value (not equal).
         * eq=fieldId:123
         */
        public Builder neq(final String fieldId, final String value) {
            super.neq(fieldId, value);
            return this;
        }

        /**
         * Numeric value (less than).
         * eq=fieldId:123
         */
        public Builder lt(final String fieldId, final String value) {
            super.lt(fieldId, value);
            return this;
        }

        /**
         * Numeric value (less than or equal to).
         * eq=fieldId:123
         */
        public Builder lte(final String fieldId, final String value) {
            super.lte(fieldId, value);
            return this;
        }

        /**
         * Numeric value (greater than).
         * eq=fieldId:123
         */
        public Builder gt(final String fieldId, final String value) {
            super.gt(fieldId, value);
            return this;
        }

        /**
         * Numeric value (grater than or equal to).
         * eq=fieldId:123
         */
        public Builder gte(final String fieldId, final String value) {
            super.gte(fieldId, value);
            return this;
        }

        /**
         * Date type period search (range).
         * yyyy-MM-dd
         */
        public Builder term(final String fieldId, final String from, final String to) {
            super.term(fieldId, from, to);
            return this;
        }

        /**
         * Date type period search (from).
         * yyyy-MM-dd
         */
        public Builder termFrom(final String fieldId, final String from) {
            super.termFrom(fieldId, from);
            return this;
        }

        /**
         * Date type period search (to).
         * yyyy-MM-dd
         */
        public Builder termTo(final String fieldId, final String to) {
            super.termTo(fieldId, to);
            return this;
        }
    }
}
