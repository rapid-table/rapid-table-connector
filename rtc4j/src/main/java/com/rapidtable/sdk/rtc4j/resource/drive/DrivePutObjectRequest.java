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

import com.rapidtable.sdk.rtc4j.resource.IPutObjectRequest;
import com.rapidtable.sdk.rtc4j.resource.PathConfig;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

public class DrivePutObjectRequest implements IPutObjectRequest {
    private final String path;
    private final String boundary;
    private final HttpRequest.BodyPublisher publisher;

    DrivePutObjectRequest(final String path, final String boundary,
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
        private String parentPath = null;
        private Path file = null;
        private String contentType = null;

        public DrivePutObjectRequest build() throws IOException {
            final var path = PathConfig.ROOT + PathConfig.WORKSPACE + String.format("/%s", workspaceId) +
                PathConfig.DRIVES + PathConfig.OBJECTS;

            final var boundary = UUID.randomUUID().toString();

            try (final var os = new ByteArrayOutputStream()) {
                os.write(("--" + boundary + "\r\n" +
                    "Content-Disposition: form-data;" +
                    " name=\"file\";" +
                    " filename*=utf-8''" + encodedFileName() + "\r\n" +
                    "Content-Type: " + contentType + "\r\n" +
                    "\r\n").getBytes());
                os.write(Files.readAllBytes(file));
                os.write(("\r\n--" + boundary + "--\r\n").getBytes());

                final var publisher = HttpRequest.BodyPublishers
                    .ofByteArray(os.toByteArray());
                return new DrivePutObjectRequest(path, boundary, publisher);
            }
        }

        public Builder workspaceId(final String workspaceId) {
            this.workspaceId = workspaceId;
            return this;
        }

        public Builder parentPath(final String parentPath) {
            this.parentPath = parentPath;
            return this;
        }

        public Builder file(final Path file, final String contentType) {
            this.file = file;
            this.contentType = contentType;
            return this;
        }

        private String encodedFileName() {
            final var paths = new ArrayList<String>();
            if (Objects.nonNull(parentPath) && !parentPath.isEmpty()) {
                paths.addAll(Arrays.asList(parentPath.split("/")));
            }
            paths.add(file.getFileName().toString());
            final var fileName = String.join("/", paths);
            return URLEncoder
                .encode(fileName, UTF_8)
                .replace("+", "%20");
        }
    }
}
