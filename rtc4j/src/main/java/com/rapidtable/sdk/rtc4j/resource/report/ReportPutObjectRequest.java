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

import com.rapidtable.sdk.rtc4j.resource.IPutObjectRequest;
import com.rapidtable.sdk.rtc4j.resource.PathConfig;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ReportPutObjectRequest implements IPutObjectRequest {
    private final String path;
    private final String boundary;
    private final HttpRequest.BodyPublisher publisher;

    ReportPutObjectRequest(final String path, final String boundary,
                           final HttpRequest.BodyPublisher publisher) {
        this.path = path;
        this.publisher = publisher;
        this.boundary = boundary;
    }

    public String getPath() {
        return path;
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
        private String reportId = null;
        private Path file = null;
        private String contentType = null;

        public ReportPutObjectRequest build() throws IOException {
            final var path = PathConfig.ROOT + PathConfig.WORKSPACE + String.format("/%s", workspaceId) +
                PathConfig.PROJECTS + String.format("/%s", projectId) +
                PathConfig.REPORTS + String.format("/%s", reportId) +
                PathConfig.OBJECTS;

            final var boundary = UUID.randomUUID().toString();
            final var fileName = file.getFileName().toString();

            try (final var os = new ByteArrayOutputStream()) {
                os.write(("--" + boundary + "\r\n" +
                    "Content-Disposition: form-data;" +
                    " name=\"file\";" +
                    " filename*=utf-8''" + URLEncoder
                    .encode(fileName, UTF_8)
                    .replace("+", "%20") + "\r\n" +
                    "Content-Type: " + contentType + "\r\n" +
                    "\r\n").getBytes());
                os.write(Files.readAllBytes(file));
                os.write(("\r\n--" + boundary + "--\r\n").getBytes());

                final var publisher = HttpRequest.BodyPublishers
                    .ofByteArray(os.toByteArray());
                return new ReportPutObjectRequest(path, boundary, publisher);
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

        public Builder file(final Path file, final String contentType) {
            this.file = file;
            this.contentType = contentType;
            return this;
        }

    }
}
