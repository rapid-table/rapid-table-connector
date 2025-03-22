///*
// * Copyright Rapid Table, Inc. or its affiliates. All Rights Reserved.
// *
// * Licensed under the Apache License, Version 2.0 (the "License").
// * You may not use this file except in compliance with the License. A copy of the License is located at
// *
// * https://www.apache.org/licenses/LICENSE-2.0
// *
// * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
// * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
// * and limitations under the License.
// */
//
//package com.rapidtable.sdk.rtc4j;
//
//import com.rapidtable.sdk.rtc4j.exceptions.TooManyRequestException;
//import com.rapidtable.sdk.rtc4j.resource.drive.DriveComponentType;
//import com.rapidtable.sdk.rtc4j.resource.drive.DriveCountRequest;
//import com.rapidtable.sdk.rtc4j.resource.drive.DriveGetMetadataRequest;
//import com.rapidtable.sdk.rtc4j.resource.drive.DriveGetObjectRequest;
//import com.rapidtable.sdk.rtc4j.resource.drive.DrivePutObjectRequest;
//import com.rapidtable.sdk.rtc4j.resource.drive.DriveResponse;
//import com.rapidtable.sdk.rtc4j.resource.drive.DriveSearchRequest;
//import com.rapidtable.sdk.rtc4j.resource.project.ProjectBulkGetRequest;
//import com.rapidtable.sdk.rtc4j.resource.project.ProjectCreateRequest;
//import com.rapidtable.sdk.rtc4j.resource.project.ProjectDeleteRequest;
//import com.rapidtable.sdk.rtc4j.resource.project.ProjectExportPackageRequest;
//import com.rapidtable.sdk.rtc4j.resource.project.ProjectGenerateIdRequest;
//import com.rapidtable.sdk.rtc4j.resource.project.ProjectGetRequest;
//import com.rapidtable.sdk.rtc4j.resource.project.ProjectImportPackageRequest;
//import com.rapidtable.sdk.rtc4j.resource.project.ProjectPutCoverRequest;
//import com.rapidtable.sdk.rtc4j.resource.project.ProjectResponse;
//import com.rapidtable.sdk.rtc4j.resource.project.ProjectSearchRequest;
//import com.rapidtable.sdk.rtc4j.resource.project.SchemaField;
//import com.rapidtable.sdk.rtc4j.resource.project.SchemaFieldType;
//import com.rapidtable.sdk.rtc4j.resource.project.SchemaGetRequest;
//import com.rapidtable.sdk.rtc4j.resource.project.SchemaUpdateRequest;
//import com.rapidtable.sdk.rtc4j.resource.project.model.Locales;
//import com.rapidtable.sdk.rtc4j.resource.project.model.ProjectCreateRequestModel;
//import com.rapidtable.sdk.rtc4j.resource.project.schema.datetime.SchemaDatetimeSettings;
//import com.rapidtable.sdk.rtc4j.resource.project.schema.number.SchemaNumberSettings;
//import com.rapidtable.sdk.rtc4j.resource.project.schema.option.SchemaCheckSettings;
//import com.rapidtable.sdk.rtc4j.resource.project.schema.option.SchemaRadioSettings;
//import com.rapidtable.sdk.rtc4j.resource.project.schema.option.SchemaSelectSettings;
//import com.rapidtable.sdk.rtc4j.resource.project.schema.text.SchemaTextSettings;
//import com.rapidtable.sdk.rtc4j.resource.report.AggregateValueResponse;
//import com.rapidtable.sdk.rtc4j.resource.report.ReportAggregateValueRequest;
//import com.rapidtable.sdk.rtc4j.resource.report.ReportBulkCountRequest;
//import com.rapidtable.sdk.rtc4j.resource.report.ReportBulkGetRequest;
//import com.rapidtable.sdk.rtc4j.resource.report.ReportBulkSearchRequest;
//import com.rapidtable.sdk.rtc4j.resource.report.ReportCountRequest;
//import com.rapidtable.sdk.rtc4j.resource.report.ReportCreateRequest;
//import com.rapidtable.sdk.rtc4j.resource.report.ReportDeleteObjectRequest;
//import com.rapidtable.sdk.rtc4j.resource.report.ReportDeleteRequest;
//import com.rapidtable.sdk.rtc4j.resource.report.ReportGenerateIdRequest;
//import com.rapidtable.sdk.rtc4j.resource.report.ReportGetObjectRequest;
//import com.rapidtable.sdk.rtc4j.resource.report.ReportGetRequest;
//import com.rapidtable.sdk.rtc4j.resource.report.ReportPutObjectRequest;
//import com.rapidtable.sdk.rtc4j.resource.report.ReportResponse;
//import com.rapidtable.sdk.rtc4j.resource.report.ReportSearchRequest;
//import com.rapidtable.sdk.rtc4j.resource.report.ReportUpdateRequest;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.nio.file.Path;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.Assertions.fail;
//
//class RapidTableConnectorTest {
//    private static final String RTC4J_TEST_ACCESS_ID = System.getenv("RTC4J_TEST_ACCESS_ID");
//    private static final String RTC4J_TEST_ACCESS_KEY = System.getenv("RTC4J_TEST_ACCESS_KEY");
//    private static final String RTC4J_TEST_ENDPOINT = System.getenv("RTC4J_TEST_ENDPOINT");
//    private static final String RTC4J_TEST_WORKSPACE_ID = System.getenv("RTC4J_TEST_WORKSPACE_ID");
//    private static final String RTC4J_TEST_PROJECT_ID = System.getenv("RTC4J_TEST_PROJECT_ID");
//    private static final String RTC4J_TEST_OBJECT_ID = System.getenv("RTC4J_TEST_OBJECT_ID");
//    private static final String RTC4J_TEST_REPORT_ID = System.getenv("RTC4J_TEST_REPORT_ID");
//
//    private static final RapidTableConnector connector = RapidTableConnector.builder()
//        .accessId(RTC4J_TEST_ACCESS_ID)
//        .accessKey(RTC4J_TEST_ACCESS_KEY)
//        .endpoint(RTC4J_TEST_ENDPOINT)
//        // !CAUTION! Debugging in the local environment.
//        .secure(false)
//        .build();
//
//    RapidTableConnectorTest() {
//        final var out = Path.of("out").toFile();
//        if (!out.exists()) {
//            out.mkdir();
//        }
//    }
//
//    @Nested
//    class Drive {
//        @Test
//        void search() throws Exception {
//            final var request = DriveSearchRequest.builder()
//                .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                .componentType(DriveComponentType.DRIVE)
//                .page(0)
//                .size(15)
//                .build();
//            final var response = connector.search(request, DriveResponse.class);
//            // FIXME
//            System.out.println("Drive search = " + response.size());
//        }
//
//        @Test
//        void count() throws Exception {
//            final var request = DriveCountRequest.builder()
//                .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                .componentType(DriveComponentType.DRIVE)
//                .build();
//            final var response = connector.count(request);
//            // FIXME
//            System.out.println("Drive count = " + response);
//        }
//
//        @Test
//        void get() throws Exception {
//            final var metadataRequest = DriveGetMetadataRequest.builder()
//                .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                .objectId(RTC4J_TEST_OBJECT_ID)
//                .build();
//            final var metadata = connector.get(metadataRequest, DriveResponse.class);
//
//            // download
//            final var objectRequest = DriveGetObjectRequest.builder()
//                .path(metadata.getPath())
//                .build();
//            final var object = connector.getObject(objectRequest);
//            System.out.println("object.getContentType() = " + object.getContentType());
//            System.out.println("object.getContentLength()() = " + object.getContentLength());
//            try (final var inputStream = object.getData();
//                 final var outputStream = new FileOutputStream(Path.of("out", object.getFileName()).toFile())) {
//                byte[] buffer = new byte[1024];
//                int bytesRead;
//                while ((bytesRead = inputStream.read(buffer)) != -1) {
//                    outputStream.write(buffer, 0, bytesRead);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Test
//        void put() throws Exception {
//            final var request = DrivePutObjectRequest.builder()
//                .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                .parentPath(String.format("test/parent-path-name/%1$tY-%1$tm-%1$td_%1$tH%1$tM%1$tS", LocalDateTime.now()))
//                .file(Path.of("out", "サンプル画像用.pptx"), "application/vnd.ms-powerpoint")
//                .build();
//            final var response = connector.putObject(request);
//            System.out.println("Report putObject = " + response);
//        }
//    }
//
//    @Nested
//    class Project {
//        @Test
//        void generateId() throws Exception {
//            final var requestBuilder = ProjectGenerateIdRequest.builder()
//                .workspaceId(RTC4J_TEST_WORKSPACE_ID);
//            final var response = connector.generateId(requestBuilder.build());
//            // FIXME
//            System.out.println("Report generateId = " + response);
//        }
//
//        @Test
//        void crud() throws Exception, TooManyRequestException {
//            String id;
//            // generateId
//            {
//                final var request = ProjectGenerateIdRequest.builder()
//                    .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                    .build();
//                id = connector.generateId(request);
//            }
//            // create
//            {
//                final var model = new ProjectCreateRequestModel(id, "sdk-project2", "description", Locales.ja);
//                final var requestBuilder = ProjectCreateRequest.builder()
//                    .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                    .model(model);
//                final var response = connector.create(requestBuilder.build(), ReportResponse.class);
//                // FIXME
//                System.out.println("Report create = " + response);
//            }
//            // delete
//            {
//                final var request = ProjectDeleteRequest.builder()
//                    .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                    .projectId(id)
//                    .exterminate(true)
//                    .build();
//                connector.delete(request);
//            }
//        }
//
//
//        @Test
//        void crudIncludingCoverPaths() throws Exception, TooManyRequestException {
//            String projectId;
//            String coverPath;
//            final var projectName = "sdk-project5";
//            // generateId
//            {
//                final var request = ProjectGenerateIdRequest.builder()
//                    .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                    .build();
//                projectId = connector.generateId(request);
//            }
//            // create Cover
//            {
//                final var request = ProjectPutCoverRequest.builder()
//                    .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                    .projectId(projectId)
//                    .file(Path.of("out", "icon.png"), "image/png")
//                    .build();
//                coverPath = connector.putObject(request);
//            }
//            // create
//            {
//                final var model = new ProjectCreateRequestModel(projectId, projectName, "description", "#00FF001", coverPath, Locales.ja);
//                final var requestBuilder = ProjectCreateRequest.builder()
//                    .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                    .model(model);
//                final var response = connector.create(requestBuilder.build(), ReportResponse.class);
//                // FIXME
//                System.out.println("Report create = " + response);
//            }
//            // delete
//            {
//                final var request = ProjectDeleteRequest.builder()
//                    .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                    .projectId(projectId)
//                    .exterminate(true)
//                    .build();
//                connector.delete(request);
//            }
//        }
//
//        @Test
//        void search() throws Exception {
//            final var searchRequest = ProjectSearchRequest.builder()
//                .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                .includes(String.format("name:%s", "sdk-project3"))
//                .build();
//            try {
//                final var responses = connector.search(searchRequest, ProjectResponse.class);
//                final var results = responses
//                    .stream()
//                    .findFirst()
//                    .map(ProjectResponse::getId)
//                    .stream().toList();
//                System.out.println("results = " + results);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Test
//        void getProject() throws Exception {
//            final var request = ProjectGetRequest.builder()
//                .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                .projectId(RTC4J_TEST_PROJECT_ID)
//                .build();
//            final var response = connector.get(request, ProjectResponse.class);
//            System.out.println("response = " + response);
//        }
//
//        @Test
//        void getProjects() throws Exception, TooManyRequestException {
//            final var request = ProjectBulkGetRequest.builder()
//                .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                .ids(RTC4J_TEST_PROJECT_ID, "pZ5MQaODXEXJi5uSDUn5K")
//                .build();
//            final var response = connector.bulkGet(request, ProjectResponse.class);
//            System.out.println("response = " + response);
//        }
//
//        @Test
//        void getSchema() throws Exception {
//            final var request = SchemaGetRequest.builder()
//                .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                .projectId(RTC4J_TEST_PROJECT_ID)
//                .build();
//            final var response = connector.get(request, SchemaField[].class);
//            assertEquals(8, response.length);
//            final var settings = response[0].getSettings();
//            System.out.println("settings = " + settings);
//        }
//
//        @Test
//        void updateSchema() throws Exception, TooManyRequestException {
//            String id;
//            // generateId
//            {
//                final var request = ProjectGenerateIdRequest.builder()
//                    .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                    .build();
//                id = connector.generateId(request);
//            }
//            // create
//            {
//                final var model = new ProjectCreateRequestModel(id, "sdk-project3", "description", Locales.ja);
//                final var requestBuilder = ProjectCreateRequest.builder()
//                    .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                    .model(model);
//                final var response = connector.create(requestBuilder.build(), ReportResponse.class);
//                // FIXME
//                System.out.println("Report create = " + response);
//            }
//            // update schema
//            final var fields = new ArrayList<SchemaField>();
//            {
//                final var schemaField = new SchemaField("keyword", "キーワード", "キーワードスキーマ",
//                    SchemaFieldType.Text, false, SchemaTextSettings.defaultValue());
//                fields.add(schemaField);
//            }
//            {
//                final var schemaField = new SchemaField("number", "数字", "数字スキーマ",
//                    SchemaFieldType.Number, false, SchemaNumberSettings.defaultValue());
//                fields.add(schemaField);
//            }
//            {
//                final var schemaField = new SchemaField("dateTime", "日時", "日時スキーマ",
//                    SchemaFieldType.DateTime, false, SchemaDatetimeSettings.defaultValue());
//                fields.add(schemaField);
//            }
//            {
//                final var schemaField = new SchemaField("check", "チェック", "チェックスキーマ",
//                    SchemaFieldType.Check, false,
//                    SchemaCheckSettings.defaultValue("チェック1", "チェック2", "チェック3"));
//                fields.add(schemaField);
//            }
//            {
//                final var schemaField = new SchemaField("radio", "ラジオ", "ラジオスキーマ",
//                    SchemaFieldType.Radio, false,
//                    SchemaRadioSettings.defaultValue("ラジオ1", "ラジオ2", "ラジオ3"));
//                fields.add(schemaField);
//            }
//            {
//                final var schemaField = new SchemaField("select", "セレクト", "セレクトスキーマ",
//                    SchemaFieldType.Select, false,
//                    SchemaSelectSettings.defaultValue("セレクト1", "セレクト2", "セレクト3"));
//                fields.add(schemaField);
//            }
//            {
//                final var request = SchemaUpdateRequest.builder()
//                    .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                    .projectId(id)
//                    .fields(fields)
//                    .build();
//                final var results = connector.update(request, SchemaField[].class);
//                System.out.println("results = " + results);
//            }
//
//            // delete
//            {
//                final var request = ProjectDeleteRequest.builder()
//                    .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                    .projectId(id)
//                    .exterminate(true)
//                    .build();
//                connector.delete(request);
//            }
//        }
//
//        @Test
//        void importPackage() throws Exception {
//            final var request = ProjectImportPackageRequest.builder()
//                .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                .projectId("ZoLKw9y0v9i0xJHaiCJ30")
//                .file(Path.of("out", "プロジェクト1.zip"))
//                .forceUseReportId(true)
//                .build();
//            try {
//                final var responses = connector.importPackage(request);
//                assertEquals(0, responses.size());
//            } catch (Exception e) {
//                fail(e);
//            }
//        }
//
//        @Test
//        void exportPackage() throws Exception {
//            final var metadataRequest = ProjectExportPackageRequest.builder()
//                .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                .projectId("ZoLKw9y0v9i0xJHaiCJ30")
//                .page(0)
//                .size(15)
//                .gte("bIkJ1rPxPf", "3")
//                .build();
//            final var object = connector.getObject(metadataRequest);
//            System.out.println("object.getContentType() = " + object.getContentType());
//            System.out.println("object.getContentLength()() = " + object.getContentLength());
//
//            // download
//            try (final var inputStream = object.getData();
//                 final var outputStream = new FileOutputStream(Path.of("out", object.getFileName()).toFile())) {
//                byte[] buffer = new byte[1024];
//                int bytesRead;
//                while ((bytesRead = inputStream.read(buffer)) != -1) {
//                    outputStream.write(buffer, 0, bytesRead);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Nested
//    class Report {
//        @Test
//        void search() throws Exception {
//            final var request = ReportSearchRequest.builder()
//                .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                .projectId("ZoLKw9y0v9i0xJHaiCJ30")
//                .page(0)
//                .size(15)
//                .build();
//            final var response = connector.search(request, ReportResponse.class);
//            // FIXME
//            System.out.println("Report search = " + response.size());
//        }
//
//        @Test
//        void buildSearch() throws Exception, TooManyRequestException {
//            final var request = ReportBulkSearchRequest.builder()
//                .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                .projectIds("ZoLKw9y0v9i0xJHaiCJ30", "6fXWKAjBoV2bqq80xZAlk")
//                .gt("bIkJ1rPxPf", "3")
//                .page(0)
//                .size(100)
//                .build();
//            final var response = connector.search(request, ReportResponse.class);
//            for (final var reportResponse : response) {
//                System.out.println("Report search = " + reportResponse.getField("bIkJ1rPxPf", String.class));
//            }
//        }
//
//        @Test
//        void count() throws Exception {
//            final var request = ReportCountRequest.builder()
//                .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                .projectId("ZoLKw9y0v9i0xJHaiCJ30")
//                .gt("bIkJ1rPxPf", "3")
//                .build();
//            final var response = connector.count(request);
//            // FIXME
//            System.out.println("Report count = " + response);
//        }
//
//        @Test
//        void buildCount() throws Exception, TooManyRequestException {
//            final var request = ReportBulkCountRequest.builder()
//                .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                .projectIds("ZoLKw9y0v9i0xJHaiCJ30", "6fXWKAjBoV2bqq80xZAlk")
//                .gt("bIkJ1rPxPf", "3")
//                .build();
//            final var response = connector.count(request);
//            // FIXME
//            System.out.println("Report count = " + response);
//        }
//
//        @Test
//        void get() throws Exception {
//            final var request = ReportGetRequest.builder()
//                .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                .projectId(RTC4J_TEST_PROJECT_ID)
//                .reportId(RTC4J_TEST_REPORT_ID)
//                .build();
//            final var response = connector.get(request, ReportResponse.class);
//
//            // FIXME
//            final var testImageFieldId = "BAJNu8NgYi";
//            final var fieldValue = response.getField(testImageFieldId, List.class);
//            assertNotNull(fieldValue);
//            assertEquals(1, fieldValue.size());
//            // FIXME
//            System.out.println("fieldValue = " + fieldValue);
//
//            final var objectRequest = ReportGetObjectRequest.builder()
//                .target(fieldValue.get(0))
//                .build();
//
//            final var object = connector.getObject(objectRequest);
//            System.out.println("object.getContentType() = " + object.getContentType());
//            System.out.println("object.getContentLength()() = " + object.getContentLength());
//            try (final var inputStream = object.getData();
//                 final var outputStream = new FileOutputStream(Path.of("out", object.getFileName()).toFile())) {
//                byte[] buffer = new byte[1024];
//                int bytesRead;
//                while ((bytesRead = inputStream.read(buffer)) != -1) {
//                    outputStream.write(buffer, 0, bytesRead);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Test
//        void bulkGet() throws Exception, TooManyRequestException {
//            final var request = ReportBulkGetRequest.builder()
//                .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                .projectId(RTC4J_TEST_PROJECT_ID)
//                .ids("2dKW3t_rRCVsa782EPe0D", "KDsPyA8nkqCQ8IxjnjI4D", "n_Ujd4xm3cS-MayoWqd5G")
//                .build();
//            final var response = connector.bulkGet(request, ReportResponse.class);
//            // FIXME
//            System.out.println("Report bulkGet = " + response.size());
//        }
//
//        @Test
//        void generateId() throws Exception {
//            final var requestBuilder = ReportGenerateIdRequest.builder()
//                .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                .projectId(RTC4J_TEST_PROJECT_ID);
//            final var response = connector.generateId(requestBuilder.build());
//            // FIXME
//            System.out.println("Report generateId = " + response);
//        }
//
//        @Test
//        void create() throws Exception, TooManyRequestException {
//            final var requestBuilder = ReportCreateRequest.builder()
//                .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                .projectId(RTC4J_TEST_PROJECT_ID)
//                .append(makeDummyFields());
//            final var response = connector.create(requestBuilder.build(), ReportResponse.class);
//            // FIXME
//            System.out.println("Report create = " + response);
//        }
//
//        @Test
//        void update() throws Exception, TooManyRequestException {
//            final var request = ReportGetRequest.builder()
//                .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                .projectId(RTC4J_TEST_PROJECT_ID)
//                .reportId(RTC4J_TEST_REPORT_ID)
//                .build();
//            final var response = connector.get(request, ReportResponse.class);
//
//            // FIXME
//            final var testUpdateFieldId = "B97LjXOPr7";
//            response.setField(testUpdateFieldId,
//                response.getField(testUpdateFieldId, "", String.class) + "-update");
//
//            final var requestBuilder = ReportUpdateRequest.builder()
//                .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                .projectId(RTC4J_TEST_PROJECT_ID)
//                .append(RTC4J_TEST_REPORT_ID, response.getFields());
//            final var updated = connector.update(requestBuilder.build(), ReportResponse.class);
//            // FIXME
//            System.out.println("Report update = " + updated);
//        }
//
//        @Test
//        void delete() throws Exception, TooManyRequestException {
//            final var request = ReportSearchRequest.builder()
//                .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                .projectId(RTC4J_TEST_PROJECT_ID)
//                .size(100)
//                .gte("CssVTEBWnK", "2023-05-09T00:00:00.000Z")
//                .build();
//            final var response = connector.search(request, ReportResponse.class);
//            // FIXME
//            System.out.println("Report search = " + response.size());
//            if (!response.isEmpty()) {
//                final var ids = response.stream()
//                    .map(ReportResponse::getId)
//                    .toArray(String[]::new);
//                final var deleteRequest = ReportDeleteRequest.builder()
//                    .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                    .projectId(RTC4J_TEST_PROJECT_ID)
//                    .ids(ids)
//                    .build();
//                connector.delete(deleteRequest);
//            }
//        }
//
//        @Test
//        void putObject() throws Exception {
//            final var request = ReportPutObjectRequest.builder()
//                .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                .projectId(RTC4J_TEST_PROJECT_ID)
//                .reportId(RTC4J_TEST_REPORT_ID)
//                // FIXME
//                .file(Path.of("out", "シンボルマーク.png"), "image/png")
//                .build();
//            final var response = connector.putObject(request);
//            // FIXME
//            System.out.println("Report putObject = " + response);
//        }
//
//        @Test
//        void deleteObject_byIds() throws Exception {
//            final var request = ReportDeleteObjectRequest.builder()
//                .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                .projectId(RTC4J_TEST_PROJECT_ID)
//                .reportId(RTC4J_TEST_REPORT_ID)
//                .objectId(RTC4J_TEST_OBJECT_ID)
//                .build();
//            connector.delete(request);
//        }
//
//        @Test
//        void deleteObject_byRelativePath() throws Exception {
//            // FIXME
//            var target = "***********************";
//            final var request = ReportDeleteObjectRequest.builder()
//                .target(target)
//                .build();
//            connector.delete(request);
//        }
//
//        @Test
//        void aggregate() throws Exception, TooManyRequestException {
//            final var request = ReportAggregateValueRequest.builder()
//                .workspaceId(RTC4J_TEST_WORKSPACE_ID)
//                .projectIds("ZoLKw9y0v9i0xJHaiCJ30", "6fXWKAjBoV2bqq80xZAlk")
//                .fieldId("bIkJ1rPxPf")
//                .gt("bIkJ1rPxPf", "3")
//                .build();
//            final var response = connector.bulkGet(request, AggregateValueResponse.class);
//            // FIXME
//            for (final var aggregateValueResponse : response) {
//                System.out.println(aggregateValueResponse.getValue() + " = " + aggregateValueResponse.getCount());
//            }
//        }
//
//        private Map<String, Object> makeDummyFields() {
//            final var fields = new HashMap<String, Object>();
//            // Number
//            fields.put("bkdTVr1J51", 4);
//            // Text
//            fields.put("B97LjXOPr7", "キーワード 2");
//            // Text
//            fields.put("AIxU8hOLfM", "アルバートサウルス 2");
//            // Text
//            fields.put("WR-2A8cFUA", "<p><strong>アルバートサウルス</strong></p>");
//            // DateTime
//            fields.put("CssVTEBWnK", LocalDateTime.of(2023, 5, 10, 12, 34, 45));
//            // Date
//            fields.put("fgW1DnIC38", LocalDateTime.of(2023, 6, 11, 12, 34, 45));
//            // Time
//            fields.put("auuTZ-huou", LocalDateTime.of(2023, 5, 10, 13, 54, 2));
//            // Check
//            fields.put("PxetCrETIb", List.of("Check 2", "Check 1"));
//            // Select
//            fields.put("fQNlK73ey3", "Select 1");
//            // Radio
//            fields.put("6OY_pjJU9k", "Radio 3");
//            // Check (Mode ref)
//            fields.put("ReIynbHDvl", List.of("36", "33", "50"));
//            // Select (Mode ref)
//            fields.put("RQdGg98STz", "41");
//            // Radio (Mode ref)
//            fields.put("IjoVtmbIeu", "44");
//            // Tags
//            fields.put("SC3K_VdiqA", List.of("U2beeXU9DeFI3T3mXn129", "isEMBD-LG72_An2U0Njs5"));
//            // Phone number
//            fields.put("N8Eey-6PKW", "090-1234-7890");
//            // E-Mail
//            fields.put("bSqX1XakUG", "mail@example.com");
//            // Zip code
//            fields.put("FPrXVrNog2", "123-4567");
//            // File path (image)
//            fields.put("BAJNu8NgYi", List.of("reports/qZle8BmYD8C5GS_94pUIF/WfNzIMYphSkgBiEzqz049/NT9QWWp39m7apQluSyalc/objects/5UKFdFAnzSuUTPv_m7DY8?v=1676013531349"));
//            // File path
//            fields.put("JX_ce48L7h", List.of("reports/qZle8BmYD8C5GS_94pUIF/WfNzIMYphSkgBiEzqz049/NT9QWWp39m7apQluSyalc/objects/1Yn3PBhL_dbdCrRgtYrPH?v=1674895052825"));
//            // User Ids
//            fields.put("X_ZZVIQKqz", List.of("dKpjWr7f95Uz4zla91-AH", "eLpBldxfwl06AWoJPsGJM"));
//            // Projects
//            fields.put("0sTYzI2IPA", List.of("wz2Y1nlIujD6LBnVJPwrn"));
//            // Rate
//            fields.put("Tl4IGjgYFv", 5);
//            // Url
//            fields.put("zZiNQEjJ5F", "https://example.com");
//            return fields;
//        }
//    }
//
//    @Nested
//    class HealthCheck {
//        @Test
//        void test() {
//            assertTrue(connector.healthy());
//        }
//    }
//}
