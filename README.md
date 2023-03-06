# rapid-table-connector
RapidTableConnector provides an SDK that allows easy CRUD operations using the Rest API provided by RapidTable.

## Getting started
### Maven
### Gradle

---

## Usage - Create Connector
### Create a connection instance
```java
RapidTableConnector connector = RapidTableConnector.builder()
    .accessId("************************")
    .accessKey("developer@example.com")
    .endpoint("rapid-table.example.com")
    .build();
```

---

## Usage - Drive
### Drive - Search assets
> Search for Drive assets in any workspace.
```java
final var request = DriveSearchRequest.builder()
    .workspaceId("EXAMPLE WORKSPACE_ID")
    .componentType(DriveComponentType.DRIVE)
    .page(0)
    .size(15)
    .build();
final var response = connector.search(request, DriveResponse.class);
```

### Drive - Count assets
> Count Drive assets in this any workspace.
```java
final var request = DriveCountRequest.builder()
    .workspaceId("EXAMPLE WORKSPACE_ID")
    .componentType(DriveComponentType.DRIVE)
    .build();
final var response = connector.count(request);
```

### Drive - Get object
> Get specific object data in this any workspace
```java
final var metadataRequest = DriveGetMetadataRequest.builder()
    .workspaceId("EXAMPLE WORKSPACE_ID")
    .objectId("EXAMPLE OBJECT_ID")
    .build();
final var metadata = connector.get(metadataRequest, DriveResponse.class);

// download
final var objectRequest = DriveGetObjectRequest.builder()
    .workspaceId("EXAMPLE WORKSPACE_ID")
    .objectId("EXAMPLE OBJECT_ID")
    .build();
try (final var inputStream = connector.getObject(objectRequest);
     final var outputStream = new FileOutputStream(Path.of("out", metadata.getName()).toFile())) {
    byte[] buffer = new byte[1024];
    int bytesRead;
    while ((bytesRead = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytesRead);
    }
}
```

---

## Usage - Projects Report

### Projects - Search reports
> Search reports for a specific project in this any workspace.
```java
final var request = ReportSearchRequest.builder()
    .workspaceId("EXAMPLE WORKSPACE_ID")
    .projectId("EXAMPLE PROJECT_ID")
    .page(0)
    .size(15)
    .build();
final var response = connector.search(request, ReportResponse.class);
```

### Projects - Count reports
> Count reports for a specific project in this any workspace.
```java
final var request = ReportCountRequest.builder()
    .workspaceId("EXAMPLE WORKSPACE_ID")
    .projectId("EXAMPLE PROJECT_ID")
    .build();
final var response = connector.count(request);
```

### Projects - Get report
> Get report for a specific project in this any workspace.
```java
final var request = ReportGetRequest.builder()
    .workspaceId("EXAMPLE WORKSPACE_ID")
    .projectId("EXAMPLE PROJECT_ID")
    .reportId("EXAMPLE REPORT_ID")
    .build();
final var response = connector.get(request, ReportResponse.class);
```

### Projects - Get report object
> Get report object for a specific object data in this any workspace.
```java
final var request = ReportGetRequest.builder()
    .workspaceId("EXAMPLE WORKSPACE_ID")
    .projectId("EXAMPLE PROJECT_ID")
    .reportId("EXAMPLE REPORT_ID")
    .build();
final var response = connector.get(request, ReportResponse.class);

final var testImageFieldId = "exampleImageFieldId";
final var fieldValue = response.getField(testImageFieldId, List.class);
assertNotNull(fieldValue);
assertEquals(1, fieldValue.size());
System.out.println("fieldValue = " + fieldValue);

final var objectRequest = ReportGetObjectRequest.builder()
    .target(fieldValue.get(0))
    .build();

try (final var inputStream = connector.getObject(objectRequest);
        final var outputStream = new FileOutputStream(Path.of("out", testImageFieldId).toFile())) {
    byte[] buffer = new byte[1024];
    int bytesRead;
    while ((bytesRead = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytesRead);
    }
}
```

### Projects - Generate Report ID
> This is an endpoint that generates IDs for reports in advance.
```java
final var requestBuilder = ReportGenerateIdRequest.builder()
    .workspaceId("EXAMPLE WORKSPACE_ID")
    .projectId("EXAMPLE PROJECT_ID");
final var response = connector.generateId(requestBuilder.build());
```

### Projects - Create Reports
> Create reports for a specific project in this any workspace.
```java
final var requestBuilder = ReportCreateRequest.builder()
    .workspaceId("EXAMPLE WORKSPACE_ID")
    .projectId("EXAMPLE PROJECT_ID")
    // A sample dummy field is shown in the next section.
    .append(makeDummyFields());
final var response = connector.create(requestBuilder.build(), ReportResponse.class);
```

### Projects - Delete Reports
> Delete reports for a specific project in this any workspace.
```java
final var request = ReportSearchRequest.builder()
    .workspaceId("EXAMPLE WORKSPACE_ID")
    .projectId("EXAMPLE PROJECT_ID")
    .size(100)
    .gte("dateTimeFieldId", "2023-12-31T00:00:00.000Z")
    .build();
final var response = connector.search(request, ReportResponse.class);
System.out.println("Report search = " + response.size());
if (!response.isEmpty()) {
    final var targetReportIds = response.stream()
        .map(ReportResponse::getId)
        .toArray(String[]::new);
    final var deleteRequest = ReportDeleteRequest.builder()
        .workspaceId("EXAMPLE WORKSPACE_ID")
        .projectId("EXAMPLE PROJECT_ID")
        .ids(targetReportIds)
        .build();
    connector.delete(deleteRequest);
}
```

### Projects - Delete Report object
> Delete specific report object data in this any workspace
```java
var target = "reports/******/******/******/objects/******?v=9999999999";
final var request = ReportDeleteObjectRequest.builder()
    .target(target)
    .build();
connector.delete(request);
```

### Sample code for report registration
```java
private Map<String, Object> makeDummyFields() {
    final var fields = new HashMap<String, Object>();
    // Number
    fields.put("numberFieldId", 4);
    // Text
    fields.put("textFieldId", "keyword");
    // DateTime
    fields.put("dateTimeFieldId", LocalDateTime.of(2023, 5, 10, 12, 34, 45));
    // Date
    fields.put("dateFieldId", LocalDateTime.of(2023, 6, 11, 12, 34, 45));
    // Time
    fields.put("timeFieldId", LocalDateTime.of(2023, 5, 10, 13, 54, 2));
    // Check
    fields.put("checkFieldId", List.of("Check 2", "Check 1"));
    // Select
    fields.put("SelectFieldId", "Select 1");
    // Radio
    fields.put("radioFieldId", "Radio 3");
    // Check (Mode ref)
    fields.put("checkRefFieldId", List.of("36", "33", "50"));
    // Select (Mode ref)
    fields.put("selectRefFieldId", "41");
    // Radio (Mode ref)
    fields.put("radioRefFieldId", "44");
    // Tags
    fields.put("tagFieldId", List.of("tag-id1", "tag-id2"));
    // Phone number
    fields.put("phoneFieldId", "090-1234-7890");
    // E-Mail
    fields.put("emailFieldId", "mail@example.com");
    // Zip code
    fields.put("zipCodeFieldId", "123-4567");
    // File path
    fields.put("fileFieldId", List.of("reports/******/******/******/objects/******?v=9999999999"));
    // User Ids
    fields.put("userFieldId", List.of("user-id1", "user-id2"));
    // Projects
    fields.put("projectFieldId", List.of("project-id1"));
    // Rate
    fields.put("rateFieldId", 5);
    // Url
    fields.put("urlFieldId", "https://example.com");
    return fields;
}
```