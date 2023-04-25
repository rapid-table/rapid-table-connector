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

    public static class Builder {
        private String workspaceId = null;
        private List<String> ids = new ArrayList<>();
        private String fieldId = null;

        public ReportAggregateValueRequest build() throws TooManyRequestException {
            final var path = PathConfig.ROOT + PathConfig.WORKSPACE + String.format("/%s", workspaceId) +
                PathConfig.PROJECTS + PathConfig.REPORTS + PathConfig.AGGREGATE;

            final var params = new ArrayList<String>();

            if (ids.isEmpty()) {
                throw new IllegalArgumentException();
            }
            if (Objects.isNull(fieldId)) {
                throw new IllegalArgumentException();
            }
            if (ids.size() > 300) {
                throw new TooManyRequestException();
            }

            params.add("ids=" + String.join(",", ids));
            params.add("fieldId=" + fieldId);

            final var query = String.join("&", params);
            return new ReportAggregateValueRequest(path, query);
        }

        public Builder workspaceId(final String workspaceId) {
            this.workspaceId = workspaceId;
            return this;
        }

        /**
         * Write the project IDs separated by commas.
         * ids=project-id1,project-id2,project-id3
         */
        public Builder ids(final String... ids) {
            this.ids.addAll(Arrays.asList(ids));
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

    }
}
