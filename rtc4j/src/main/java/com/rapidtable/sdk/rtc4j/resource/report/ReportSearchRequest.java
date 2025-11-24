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

import java.util.Arrays;
import java.util.List;

public class ReportSearchRequest implements IRequest {
    private final String path;
    private final String query;

    ReportSearchRequest(final String path, final String query) {
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

    public static class Builder extends ReportSearchBuilderBase<ReportSearchRequest> {
        private Boolean metadata = false;

        public Builder workspaceId(final String workspaceId) {
            this.workspaceId = workspaceId;
            return this;
        }

        public Builder projectId(final String projectId) {
            this.projectId = projectId;
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

        /**
         * Numeric value (equals).
         * eq=fieldId:123
         */
        public Builder eq(final String fieldId, final String value) {
            this.eq = String.format("%s:%s", fieldId, value);
            return this;
        }

        /**
         * Numeric value (not equal).
         * eq=fieldId:123
         */
        public Builder neq(final String fieldId, final String value) {
            this.neq = String.format("%s:%s", fieldId, value);
            return this;
        }

        /**
         * Numeric value (less than).
         * eq=fieldId:123
         */
        public Builder lt(final String fieldId, final String value) {
            this.lt = String.format("%s:%s", fieldId, value);
            return this;
        }

        /**
         * Numeric value (less than or equal to).
         * eq=fieldId:123
         */
        public Builder lte(final String fieldId, final String value) {
            this.lte = String.format("%s:%s", fieldId, value);
            return this;
        }

        /**
         * Numeric value (greater than).
         * eq=fieldId:123
         */
        public Builder gt(final String fieldId, final String value) {
            this.gt = String.format("%s:%s", fieldId, value);
            return this;
        }

        /**
         * Numeric value (grater than or equal to).
         * eq=fieldId:123
         */
        public Builder gte(final String fieldId, final String value) {
            this.gte = String.format("%s:%s", fieldId, value);
            return this;
        }

        /**
         * Date type period search (range).
         * yyyy-MM-dd
         */
        public Builder term(final String fieldId, final String from, final String to) {
            this.term = String.format("%s:%s,%s", fieldId, from, to);
            return this;
        }

        /**
         * Date type period search (from).
         * yyyy-MM-dd
         */
        public Builder termFrom(final String fieldId, final String from) {
            this.term = String.format("%s:%s,", fieldId, from);
            return this;
        }

        /**
         * Date type period search (to).
         * yyyy-MM-dd
         */
        public Builder termTo(final String fieldId, final String to) {
            this.term = String.format("%s:,%s", fieldId, to);
            return this;
        }

        /**
         * Write the report IDs separated by commas.
         * ids=report-id1,report-id2,report-id3
         */
        public Builder ids(final String... ids) {
            this.ids.addAll(Arrays.asList(ids));
            return this;
        }

        /**
         * If "true", you can get report metadata (created date/updated date).
         * Available from RapidTable version 1.6.25
         */
        public ReportSearchBuilderBase<ReportSearchRequest> metadata(final Boolean enabled) {
            this.metadata = enabled;
            return this;
        }

        @Override
        public ReportSearchRequest build() {
            final var path = PathConfig.ROOT + PathConfig.WORKSPACE + String.format("/%s", workspaceId) +
                PathConfig.PROJECTS + String.format("/%s", projectId) +
                PathConfig.REPORTS +
                PathConfig.SEARCH;
            final var query = String.join("&", getParams());
            return new ReportSearchRequest(path, query);
        }

        @Override
        public List<String> getParams() {
            final var params = super.getParams();
            if (metadata) {
                params.add("metadata=true");
            }
            return params;
        }
    }
}
