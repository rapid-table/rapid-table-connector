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

import com.rapidtable.sdk.rtc4j.resource.IImportPackageRequest;
import com.rapidtable.sdk.rtc4j.resource.PathConfig;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class ProjectImportPackageRequest implements IImportPackageRequest {
    private final String path;
    private final String query;
    private final String boundary;
    private final HttpRequest.BodyPublisher publisher;

    ProjectImportPackageRequest(final String path, final String query, final String boundary,
                                final HttpRequest.BodyPublisher publisher) {
        this.path = path;
        this.query = query;
        this.publisher = publisher;
        this.boundary = boundary;
    }

    public String getPath() {
        return path;
    }

    public String getQuery() {
        return query;
    }

    @Override
    public String getBoundary() {
        return boundary;
    }

    @Override
    public HttpRequest.BodyPublisher getPublisher() {
        return publisher;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String workspaceId = null;
        private String projectId = null;
        private Boolean isForceUseReportId = false;
        private Path file = null;

        public ProjectImportPackageRequest build() throws IOException {
            final var path = PathConfig.ROOT + PathConfig.WORKSPACE + String.format("/%s", workspaceId) +
                PathConfig.PROJECTS + String.format("/%s", projectId) +
                "/package/import";

            final var query = isForceUseReportId
                ? "force-use-report-id=true"
                : null;

            try (final var os = new ByteArrayOutputStream()) {
                final var boundary = UUID.randomUUID().toString();
                os.write(("--" + boundary + "\r\n" +
                    "Content-Disposition: form-data;" +
                    " name=\"file\";" +
                    " filename*=utf-8''package.zip\r\n" +
                    "Content-Type: application/zip\r\n" +
                    "\r\n").getBytes());
                os.write(Files.readAllBytes(file));
                os.write(("\r\n--" + boundary + "--\r\n").getBytes());

                final var publisher = HttpRequest.BodyPublishers
                    .ofByteArray(os.toByteArray());
                return new ProjectImportPackageRequest(path, query, boundary, publisher);
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

        public Builder forceUseReportId(final Boolean forceUseReportId) {
            this.isForceUseReportId = forceUseReportId;
            return this;
        }

        public Builder file(final Path file) {
            this.file = file;
            return this;
        }
    }
}
