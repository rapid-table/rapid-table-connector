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

package com.rapidtable.sdk.rtc4j;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rapidtable.sdk.rtc4j.resource.GetObjectResponse;
import com.rapidtable.sdk.rtc4j.resource.IDeleteRequest;
import com.rapidtable.sdk.rtc4j.resource.IGenerateIdRequest;
import com.rapidtable.sdk.rtc4j.resource.IPutObjectRequest;
import com.rapidtable.sdk.rtc4j.resource.IRequest;
import com.rapidtable.sdk.rtc4j.resource.PathConfig;

import java.net.URI;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.temporal.ChronoUnit.MINUTES;

public class RapidTableConnector {
    private static final ObjectMapper mapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .registerModule(new JavaTimeModule());

    private final String accessId;
    private final String accessKey;
    private final String host;
    private final HttpClient client;
    private final String schema;
    private final String AUTHORIZATION = "Authorization";
    private final String HTTP_CONTENT_TYPE_HEADER = "Content-Type";
    private final String HTTP_CONTENT_TYPE_JSON_VALUE = "application/json; charset=utf-8";
    private Credentials credentials = Credentials.empty();

    public RapidTableConnector(final String accessId, final String accessKey,
                               final String host, final Boolean secure) {
        this.accessId = accessId;
        this.accessKey = accessKey;
        this.host = host;
        this.client = HttpClient.newHttpClient();
        this.schema = secure ? "https" : "http";
    }

    public <T> List<T> search(final IRequest request, final Class<T> classType) throws Exception {
        if (!credentials.isPermitted()) {
            this.credentials = permission();
        }

        if (Objects.isNull(request.getPath())) {
            throw new IllegalArgumentException();
        }

        final var httpRequestBuilder = HttpRequest.newBuilder()
            .GET()
            .uri(new URI(schema, host, request.getPath(), request.getQuery(), null))
            .setHeader(HTTP_CONTENT_TYPE_HEADER, HTTP_CONTENT_TYPE_JSON_VALUE)
            .setHeader(AUTHORIZATION, credentials.token());

        final var httpRequest = httpRequestBuilder
            .build();

        final var response = client
            .send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new Exception();
        }
        final var body = response.body();

        final var collectionType = mapper.getTypeFactory()
            .constructCollectionType(List.class, classType);

        return mapper.readValue(body, collectionType);
    }

    public long count(final IRequest request) throws Exception {
        if (!credentials.isPermitted()) {
            this.credentials = permission();
        }

        if (Objects.isNull(request.getPath())) {
            throw new IllegalArgumentException();
        }

        final var httpRequestBuilder = HttpRequest.newBuilder()
            .GET()
            .uri(new URI(schema, host, request.getPath(), request.getQuery(), null))
            .setHeader(HTTP_CONTENT_TYPE_HEADER, HTTP_CONTENT_TYPE_JSON_VALUE)
            .setHeader(AUTHORIZATION, credentials.token());

        final var httpRequest = httpRequestBuilder
            .build();

        final var response = client
            .send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new Exception();
        }
        final var body = response.body();
        return Long.parseLong(body);
    }

    public <T> T get(final IRequest request, final Class<T> classType) throws Exception {
        if (!credentials.isPermitted()) {
            this.credentials = permission();
        }

        if (Objects.isNull(request.getPath())) {
            throw new IllegalArgumentException();
        }

        final var httpRequest = HttpRequest.newBuilder()
            .GET()
            .uri(new URI(schema, host, request.getPath(), request.getQuery(), null))
            .setHeader(HTTP_CONTENT_TYPE_HEADER, HTTP_CONTENT_TYPE_JSON_VALUE)
            .setHeader(AUTHORIZATION, credentials.token())
            .build();

        final var response = client
            .send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new Exception();
        }
        return mapper.readValue(response.body(), classType);
    }

    public <T> List<T> bulkGet(final IRequest request, final Class<T> classType) throws Exception {
        if (!credentials.isPermitted()) {
            this.credentials = permission();
        }

        if (Objects.isNull(request.getPath())) {
            throw new IllegalArgumentException();
        }

        final var httpRequest = HttpRequest.newBuilder()
            .GET()
            .uri(new URI(schema, host, request.getPath(), request.getQuery(), null))
            .setHeader(HTTP_CONTENT_TYPE_HEADER, HTTP_CONTENT_TYPE_JSON_VALUE)
            .setHeader(AUTHORIZATION, credentials.token())
            .build();

        final var response = client
            .send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new Exception();
        }
        final var body = response.body();

        final var collectionType = mapper.getTypeFactory()
            .constructCollectionType(List.class, classType);

        return mapper.readValue(body, collectionType);
    }

    public GetObjectResponse getObject(final IRequest request) throws Exception {
        if (!credentials.isPermitted()) {
            this.credentials = permission();
        }

        if (Objects.isNull(request.getPath())) {
            throw new IllegalArgumentException();
        }

        final var httpRequest = HttpRequest.newBuilder()
            .GET()
            .uri(new URI(schema, host, request.getPath(), request.getQuery(), null))
            .setHeader(HTTP_CONTENT_TYPE_HEADER, HTTP_CONTENT_TYPE_JSON_VALUE)
            .setHeader(AUTHORIZATION, credentials.token())
            .build();

        final var response = client
            .send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());
        if (response.statusCode() != 200) {
            throw new Exception();
        }
        final var fileName = response.headers()
            .firstValue("etag")
            .map(value -> URLDecoder.decode(value, UTF_8))
            .orElse("");
        final var contentType = response.headers()
            .firstValue("content-type")
            .orElse("");
        final var contentLength = response.headers()
            .firstValueAsLong("content-length")
            .orElse(0L);
        return new GetObjectResponse(fileName, contentType, contentLength, response.body());
    }

    public String putObject(final IPutObjectRequest request) throws Exception {
        if (!credentials.isPermitted()) {
            this.credentials = permission();
        }

        if (Objects.isNull(request.getPath())) {
            throw new IllegalArgumentException();
        }

        try {
            final var httpRequest = HttpRequest.newBuilder()
                .uri(new URI(schema, host, request.getPath(), null, null))
                .PUT(request.getPublisher())
                .setHeader(AUTHORIZATION, credentials.token())
                .setHeader(HTTP_CONTENT_TYPE_HEADER, request.getContentType())
                .build();

            final var response = client
                .send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new Exception();
            }
            return response.body();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return null;
    }

    public String generateId(final IGenerateIdRequest request) throws Exception {
        if (!credentials.isPermitted()) {
            this.credentials = permission();
        }

        if (Objects.isNull(request.getPath())) {
            throw new IllegalArgumentException();
        }

        final var httpRequest = HttpRequest.newBuilder()
            .GET()
            .uri(new URI(schema, host, request.getPath(), null, null))
            .setHeader(HTTP_CONTENT_TYPE_HEADER, HTTP_CONTENT_TYPE_JSON_VALUE)
            .setHeader(AUTHORIZATION, credentials.token())
            .build();

        final var response = client
            .send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new Exception();
        }
        return response.body();
    }

    public <T> List<T> create(final IRequest request, final Class<T> classType) throws Exception {
        if (!credentials.isPermitted()) {
            this.credentials = permission();
        }

        if (Objects.isNull(request.getPath())) {
            throw new IllegalArgumentException();
        }

        final var httpRequest = HttpRequest.newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString(request.getBody()))
            .uri(new URI(schema, host, request.getPath(), request.getQuery(), null))
            .setHeader(HTTP_CONTENT_TYPE_HEADER, HTTP_CONTENT_TYPE_JSON_VALUE)
            .setHeader(AUTHORIZATION, credentials.token())
            .build();

        final var response = client
            .send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new Exception();
        }
        final var collectionType = mapper.getTypeFactory()
            .constructCollectionType(List.class, classType);

        return mapper.readValue(response.body(), collectionType);
    }

    public <T> List<T> update(final IRequest request, final Class<T> classType) throws Exception {
        if (!credentials.isPermitted()) {
            this.credentials = permission();
        }

        if (Objects.isNull(request.getPath())) {
            throw new IllegalArgumentException();
        }

        final var httpRequest = HttpRequest.newBuilder()
            .PUT(HttpRequest.BodyPublishers.ofString(request.getBody()))
            .uri(new URI(schema, host, request.getPath(), request.getQuery(), null))
            .setHeader(HTTP_CONTENT_TYPE_HEADER, HTTP_CONTENT_TYPE_JSON_VALUE)
            .setHeader(AUTHORIZATION, credentials.token())
            .build();

        final var response = client
            .send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new Exception();
        }
        final var collectionType = mapper.getTypeFactory()
            .constructCollectionType(List.class, classType);

        return mapper.readValue(response.body(), collectionType);
    }

    public void delete(final IDeleteRequest request) throws Exception {
        if (!credentials.isPermitted()) {
            this.credentials = permission();
        }

        if (Objects.isNull(request.getPath())) {
            throw new IllegalArgumentException();
        }

        final var httpRequest = HttpRequest.newBuilder()
            .DELETE()
            .uri(new URI(schema, host, request.getPath(), request.getQuery(), null))
            .setHeader(HTTP_CONTENT_TYPE_HEADER, HTTP_CONTENT_TYPE_JSON_VALUE)
            .setHeader(AUTHORIZATION, credentials.token())
            .build();

        final var response = client
            .send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new Exception();
        }
    }

    private Credentials permission() throws Exception {
        final var body = String.format("{\"email\": \"%s\", \"key\": \"%s\"}", accessId, accessKey);
        final var httpRequest = HttpRequest.newBuilder()
            .uri(new URI(schema, host, PathConfig.ROOT + PathConfig.PERMISSIONS, null, null))
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .setHeader(HTTP_CONTENT_TYPE_HEADER, HTTP_CONTENT_TYPE_JSON_VALUE)
            .build();

        final var response = client
            .send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new Exception();
        }
        final var token = response.headers()
            .firstValue(AUTHORIZATION)
            .orElseThrow(Exception::new);
        return Credentials.approve(token);
    }

    // #region builder methods
    public static RapidTableConnectorBuilder builder() {
        return new RapidTableConnectorBuilder();
    }

    public static class RapidTableConnectorBuilder {
        private String accessId;
        private String accessKey;
        private String endpoint;
        private boolean secure;

        public RapidTableConnectorBuilder() {
            accessId = null;
            accessKey = null;
            endpoint = null;
            secure = true;
        }

        public RapidTableConnectorBuilder accessId(final String accessId) {
            this.accessId = accessId;
            return this;
        }

        public RapidTableConnectorBuilder accessKey(final String accessKey) {
            this.accessKey = accessKey;
            return this;
        }

        public RapidTableConnectorBuilder endpoint(final String endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        public RapidTableConnectorBuilder secure(final Boolean secure) {
            this.secure = secure;
            return this;
        }

        public RapidTableConnector build() {
            if (Objects.isNull(accessId)) {
                throw new IllegalArgumentException("accessId is required.");
            }
            if (Objects.isNull(accessKey)) {
                throw new IllegalArgumentException("accessKey is required.");
            }
            if (Objects.isNull(endpoint)) {
                throw new IllegalArgumentException("endpoint is required.");
            }
            return new RapidTableConnector(accessId, accessKey, endpoint, secure);
        }
    }
    // #endregion

    record Credentials(String token, LocalDateTime approvedAt) {
        boolean isPermitted() {
            if (Objects.isNull(token)) {
                return false;
            }
            final var diff = MINUTES.between(LocalDateTime.now(), approvedAt);
            return Math.abs(diff) <= 50;
        }

        static Credentials empty() {
            return new Credentials(null, LocalDateTime.MIN);
        }

        static Credentials approve(String token) {
            return new Credentials(token, LocalDateTime.now());
        }
    }
}
