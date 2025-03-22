# RapidTableConnector for Javascript
RapidTableConnector provides an SDK that allows easy CRUD operations using the Rest API provided by [RapidTable](https://rapid-table.com/).


[![npm](https://img.shields.io/npm/v/rtc4js.svg)](https://www.npmjs.com/package/rtc4js)
[![GitHub license](https://img.shields.io/github/license/rapid-table/rapid-table-connector.svg)](https://github.com/rapid-table/rapid-table-connector)
[![npm](https://img.shields.io/bundlephobia/min/rtc4js.svg)](https://www.npmjs.com/package/rtc4js)

---

## Getting started
### Installation
```
npm install rtc4js
```

---

## Usage - Create Connector
### Create a connection instance
```js
import { RapidTableConnector } from 'rtc4js';

const connector = RapidTableConnector.builder()
    .accessId("developer@example.com")
    .accessKey("************************")
    .endpoint("rapid-table.example.com")
    .build();
```

---

## Usage - Drive
### Drive - Search assets
> Search for Drive assets in any workspace.
```js
import { DriveComponentType, DriveResponse, DriveSearchRequest } from 'rtc4js';

const request = DriveSearchRequest.builder()
    .workspaceId('RTC4J_TEST_WORKSPACE_ID')
    .componentType(DriveComponentType.DRIVE)
    .page(0)
    .size(15)
    .build();
const results = await connector.search(request, DriveResponse.of);
console.log('results', results);
```

### Drive - Count assets
> Count Drive assets in this any workspace.
```js
import { DriveComponentType, DriveCountRequest } from 'rtc4js';

const request = DriveCountRequest.builder()
    .workspaceId('RTC4J_TEST_WORKSPACE_ID')
    .componentType(DriveComponentType.REPORT)
    .query('png')
    .build();
const results = await connector.count(request);
console.log('results', results);
```

### Drive - Get object
> Get specific object data in this any workspace
```js
import * as fs from 'fs';
import { DriveGetMetadataRequest, metadataRequest, DriveResponse, DriveGetObjectRequest } from 'rtc4js';

const metadataRequest = DriveGetMetadataRequest.builder()
    .workspaceId('RTC4J_TEST_WORKSPACE_ID')
    .objectId('RTC4J_TEST_OBJECT_ID')
    .build();
try {
    const metadata = await connector.get(metadataRequest, DriveResponse.of);

    // download
    const objectRequest = DriveGetObjectRequest.builder()
        .path(metadata.path)
        .build();
    const object = await connector.getObject(objectRequest);
    fs.writeFileSync(object.fileName, object.data, 'binary');
} catch (ex) {
    console.error(ex);
}
```

### Drive - Put object
> Upload an object to a workspace drive.
```js
import * as fs from 'fs';
import { DrivePutObjectRequest } from 'rtc4js';

const buffer = await fs.readFileSync('example.jpg');
const request = DrivePutObjectRequest.builder()
    .workspaceId('RTC4J_TEST_WORKSPACE_ID')
    .append(buffer, 'root/example.jpg')
    .build();
const path = await connector.putObject(request);
```
---

## Usage - Projects Report

### Projects - Get schema
> Gets schema information within a specific project in this any workspace.
```js
const request = SchemaGetRequest.builder()
    .workspaceId('RTC4J_TEST_WORKSPACE_ID')
    .projectId('RTC4J_TEST_PROJECT_ID')
    .build();
const results = await connector.get<SchemaField[]>(request);
```

### Projects - Put the cover image
> Upload a cover image for your project.
```js
import { ProjectPutCoverRequest } from 'rtc4js';

const buffer = await fs.readFileSync('example.jpg');
const request = ProjectPutCoverRequest.builder()
    .workspaceId("EXAMPLE WORKSPACE_ID")
    .projectId("EXAMPLE PROJECT_ID")
    .append(buffer, "image/png")
    .build();
const coverPath = await connector.putObject(request);
```

### Projects - Search reports
> Search reports for a specific project in this any workspace.
```js
import { ReportSearchRequest, ReportResponse } from 'rtc4js';

const request = ReportSearchRequest.builder()
    .workspaceId('RTC4J_TEST_WORKSPACE_ID')
    .projectId('RTC4J_TEST_PROJECT_ID')
    .page(0)
    .size(15)
    .build();
const results = await connector.search(request, ReportResponse.of);
console.log('results', results);
```

### Projects - Search reports (Bulk operation)
> Search reports for multiple projects in this any workspace.
```js
import { ReportBulkSearchRequest, ReportResponse } from 'rtc4js';

const request = ReportBulkSearchRequest.builder()
    .workspaceId("EXAMPLE WORKSPACE_ID")
    .projectIds("EXAMPLE PROJECT_ID1", "EXAMPLE PROJECT_ID2")
    .page(0)
    .size(15)
    .build();
const response = connector.search(request, ReportResponse.of);
```

### Projects - Count reports
> Count reports for a specific project in this any workspace.
```js
import { ReportCountRequest } from 'rtc4js';

const request = ReportCountRequest.builder()
    .workspaceId('RTC4J_TEST_WORKSPACE_ID')
    .projectId('RTC4J_TEST_PROJECT_ID')
    .build();
const results = await connector.count(request);
```

### Projects - Count reports (Bulk operation)
> Count reports for for multiple projects in this any workspace.
```js
import { ReportBulkCountRequest } from 'rtc4js';

const request = ReportBulkCountRequest.builder()
    .workspaceId('RTC4J_TEST_WORKSPACE_ID')
    .projectIds("EXAMPLE PROJECT_ID1", "EXAMPLE PROJECT_ID2")
    .build();
const results = await connector.count(request);
```

### Projects - Get report
> Get report for a specific project in this any workspace.
```js
import { ReportGetRequest, ReportResponse } from 'rtc4js';

const request = ReportGetRequest.builder()
    .workspaceId('RTC4J_TEST_WORKSPACE_ID')
    .projectId('RTC4J_TEST_PROJECT_ID')
    .reportId('RTC4J_TEST_REPORT_ID')
    .build();
const report = await connector.get(request, ReportResponse.of);
console.log('report', report);
```

### Projects - Get reports (Bulk operation)
> Get report for a specific project in this any workspace.
```js
import { ReportBulkGetRequest, ReportResponse } from 'rtc4js';

const request = ReportBulkGetRequest.builder()
    .workspaceId('RTC4J_TEST_WORKSPACE_ID')
    .projectId('RTC4J_TEST_PROJECT_ID')
    .ids("EXAMPLE REPORT_ID1", "EXAMPLE REPORT_ID2", "EXAMPLE REPORT_ID3")
    .build();
const reports = await connector.bulkGet(request, ReportResponse.of);
console.log('reports', reports);
```

### Projects - Get report object
> Get report object for a specific object data in this any workspace.
```js
import * as fs from 'fs';
import { ReportGetRequest, ReportResponse, ReportGetObjectRequest } from 'rtc4js';

const request = ReportGetRequest.builder()
    .workspaceId('RTC4J_TEST_WORKSPACE_ID')
    .projectId('RTC4J_TEST_PROJECT_ID')
    .reportId('RTC4J_TEST_REPORT_ID')
    .build();
try {
    const report = await connector.get(request, ReportResponse.of);
    console.log('report', report);
    const testImageFieldId = 'BAJNu8NgYi';
    const images = report.getFieldAsList(testImageFieldId);

    if (images.length) {
        // download
        const objectRequest = ReportGetObjectRequest.builder()
            .target(images[0])
            .build();
        const object = await connector.getObject(objectRequest);
        console.log('data', object.fileName, object.contentType, object.contentLength);
        fs.writeFileSync(object.fileName, object.data, 'binary');
    }
} catch (ex) {
    console.error(ex);
}
```

### Projects - Generate Report ID
> This is an endpoint that generates IDs for reports in advance.
```js
import { ReportGenerateIdRequest } from 'rtc4js';

const requestBuilder = ReportGenerateIdRequest.builder()
    .workspaceId('RTC4J_TEST_WORKSPACE_ID')
    .projectId('RTC4J_TEST_PROJECT_ID')
    .build();
const response = await connector.generateId(requestBuilder);
console.log('Report generateId = ' + response);
```

### Projects - Create Reports
> Create reports for a specific project in this any workspace.
```js
import { ReportCreateRequest, ReportResponse } from 'rtc4js';

const requestBuilder = ReportCreateRequest.builder()
    .workspaceId('RTC4J_TEST_WORKSPACE_ID')
    .projectId('RTC4J_TEST_PROJECT_ID')
    // A sample dummy field is shown in the next section.
    .append(makeDummyFields())
    .build();
const response = await connector.create(requestBuilder, ReportResponse.of);
// FIXME
console.log('Report create = ' + response);
```

### Projects - Update Reports
> Update reports for a specific project in this any workspace.
```js
import { ReportGetRequest, ReportUpdateRequest, ReportResponse } from 'rtc4js';

// Get report
const request = ReportGetRequest.builder()
    .workspaceId('RTC4J_TEST_WORKSPACE_ID')
    .projectId('RTC4J_TEST_PROJECT_ID')
    .reportId('RTC4J_TEST_REPORT_ID')
    .build();
const response = await connector.get(request, ReportResponse.of);

// Update report
const testUpdateFieldId = 'B97LjXOPr7';
response.setField(testUpdateFieldId, response.getFieldAsString(testUpdateFieldId) + '-update');

const requestBuilder = ReportUpdateRequest.builder()
    .workspaceId('RTC4J_TEST_WORKSPACE_ID')
    .projectId('RTC4J_TEST_PROJECT_ID')
    .append('RTC4J_TEST_REPORT_ID', response.fields)
    .build();
const updated = await connector.update(requestBuilder, ReportResponse.of);
console.log('Report update = ' + updated);
```

### Projects - Delete Reports
> Delete reports for a specific project in this any workspace.
```js
import { ReportSearchRequest, ReportDeleteRequest, ReportResponse } from 'rtc4js';

// Search reports
const request = ReportSearchRequest.builder()
    .workspaceId('RTC4J_TEST_WORKSPACE_ID')
    .projectId('RTC4J_TEST_PROJECT_ID')
    .size(100)
    .gte('CssVTEBWnK', '2023-05-09T00:00:00.000Z')
    .build();
const response = await connector.search(request, ReportResponse.of);
console.log('Report search = ' + response.length);

if (response.length) {
    // Delete reports
    const ids = response.map(({ id }) => id);
    const deleteRequest = ReportDeleteRequest.builder()
        .workspaceId('RTC4J_TEST_WORKSPACE_ID')
        .projectId('RTC4J_TEST_PROJECT_ID')
        .ids(...ids)
        .build();
    await connector.delete(deleteRequest);
}
```

### Projects - Put report object
> Put report object for a specific project in this any workspace.
```js
import * as fs from 'fs';
import { ReportPutObjectRequest } from 'rtc4js';

const buffer = await fs.readFileSync('example.jpg');
const request = ReportPutObjectRequest.builder()
    .workspaceId('RTC4J_TEST_WORKSPACE_ID')
    .projectId('RTC4J_TEST_PROJECT_ID')
    .reportId('RTC4J_TEST_REPORT_ID')
    .append(buffer, 'example.jpg')
    .build();
const response = await connector.putObject(request);
console.log('Report putObject = ' + response);
```

### Projects - Delete Report object
> Delete specific report object data in this any workspace
```js
import { ReportDeleteObjectRequest } from 'rtc4js';

// FIXME
const target = "reports/******/******/******/objects/******?v=9999999999";
const request = ReportDeleteObjectRequest.builder()
    .target(target)
    .build();
await connector.delete(request);
```

### Projects - Aggregate report values
> Aggregates the values ​​of a report field for multiple projects in this any workspace
```js
import { ReportAggregateValueRequest, AggregateValueResponse } from 'rtc4js';

const request = ReportAggregateValueRequest.builder()
    .workspaceId('RTC4J_TEST_WORKSPACE_ID')
    .projectIds("EXAMPLE PROJECT_ID1", "EXAMPLE PROJECT_ID2")
    .fieldId("EXAMPLE_TARGET_FIELD_ID")
    .build();
const results = await connector.bulkGet(request, AggregateValueResponse.of);
```

### Projects - Import package
> This API uses the package import feature to import reports in bulk (import is in ZIP file format).
```js
import * as fs from 'fs';
import { ProjectImportPackageRequest } from 'rtc4js';

const buffer = await fs.readFileSync('out/package.zip');
const request = ProjectImportPackageRequest.builder()
    .workspaceId('RTC4J_TEST_WORKSPACE_ID')
    .projectId('RTC4J_TEST_PROJECT_ID')
    .append(buffer)
    .build();
const response = await connector.importPackage(request);
```

### Projects - Export package
> This API uses the package export feature to export reports in bulk.
```js
import * as fs from 'fs';
import { ProjectExportPackageRequest } from 'rtc4js';

const request = ProjectExportPackageRequest.builder()
    .workspaceId('RTC4J_TEST_WORKSPACE_ID')
    .projectId('RTC4J_TEST_PROJECT_ID')
    .gte("fieldId", "specific value")
    .build();
try {
    const object = await connector.getObject(request);
    fs.writeFileSync(object.fileName, object.data, 'binary');
} catch (ex) {
    console.error(ex);
}
```

### Sample code for report registration
```js
function makeDummyFields(): { [key: string]: unknown } {
    const fields: { [key: string]: unknown } = {};
    // Number
    fields['numberFieldId'] = 4;
    // Text
    fields['textFieldId'] = 'キーワード 3';
    // DateTime
    fields['dateTimeFieldId'] = new Date(2023, 5, 10, 12, 34, 45);
    // Date
    fields['dateFieldId'] = new Date(2023, 6, 11, 12, 34, 45);
    // Time
    fields['timeFieldId'] = new Date(2023, 5, 10, 13, 54, 2);
    // Check
    fields['checkFieldId'] = ['Check 2', 'Check 1'];
    // Select
    fields['SelectFieldId'] = 'Select 1';
    // Radio
    fields['radioFieldId'] = 'Radio 3';
    // Check (Mode ref)
    fields['checkRefFieldId'] = ['36', '33', '50'];
    // Select (Mode ref)
    fields['selectRefFieldId'] = '41';
    // Radio (Mode ref)
    fields['radioRefFieldId'] = '44';
    // Tags
    fields['tagFieldId'] = ['tagId1', 'tagId2'];
    // Phone number
    fields['phoneFieldId'] = '090-1234-7890';
    // E-Mail
    fields['emailFieldId'] = 'mail@example.com';
    // Zip code
    fields['zipCodeFieldId'] = '123-4567';
    // File path
    fields['fileFieldId'] = ['reports/workspaceId/projectId/reportId/objects/objectId?v=1674895052825'];
    // User Ids
    fields['userFieldId'] = ['userId1', 'userId2'];
    // Projects
    fields['projectFieldId'] = ['projectId'];
    // Rate
    fields['rateFieldId'] = 5;
    // Url
    fields['urlFieldId'] = 'https://example.com';
    return fields;
}
```