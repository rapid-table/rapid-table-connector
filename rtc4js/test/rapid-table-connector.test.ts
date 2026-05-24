/*
 * Copyright Rapid Table, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the 'License').
 * You may not use this file except in compliance with the License. A copy of the License is located at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * or in the 'license' file accompanying this file. This file is distributed on an 'AS IS' BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

import dotenv from 'dotenv';
import * as fs from 'fs';

import { expect, test } from '@jest/globals';
import { RapidTableConnector } from '../src/rapid-table-connector';
import { DriveComponentType } from '../src/resource/drive/drive-component-type';
import { DriveCountRequest } from '../src/resource/drive/drive-count-request';
import { DriveGetMetadataRequest } from '../src/resource/drive/drive-get-metadata-request';
import { DriveGetObjectRequest } from '../src/resource/drive/drive-get-object-request';
import { DrivePutObjectRequest } from '../src/resource/drive/drive-put-object-request';
import { DriveResponse } from '../src/resource/drive/drive-response';
import { DriveSearchRequest } from '../src/resource/drive/drive-search-request';
import { GetObjectResponse } from '../src/resource/get-object-response';
import { ProjectCreateRequest } from '../src/resource/project/project-create-request';
import { ProjectExportPackageRequest } from '../src/resource/project/project-export-package-request';
import { ProjectGenerateIdRequest } from '../src/resource/project/project-generate-id-request';
import { ProjectImportPackageRequest } from '../src/resource/project/project-import-package-request';
import { ProjectPutCoverRequest } from '../src/resource/project/project-put-cover-request';
import { ProjectRequestModel } from '../src/resource/project/project-request-model';
import { ProjectResponse } from '../src/resource/project/project-response';
import { SchemaField } from '../src/resource/project/schema-field';
import { SchemaGetRequest } from '../src/resource/project/schema-get-request';
import { AggregateValueResponse } from '../src/resource/report/aggregate-value-response';
import { ReportAggregateValueRequest } from '../src/resource/report/report-aggregate-value-request';
import { ReportBulkCountRequest } from '../src/resource/report/report-bulk-count-request';
import { ReportBulkGetRequest } from '../src/resource/report/report-bulk-get-request';
import { ReportBulkSearchRequest } from '../src/resource/report/report-bulk-search-request';
import { ReportCountRequest } from '../src/resource/report/report-count-request';
import { ReportCreateRequest } from '../src/resource/report/report-create-request';
import { ReportDeleteObjectRequest } from '../src/resource/report/report-delete-object-request';
import { ReportDeleteRequest } from '../src/resource/report/report-delete-request';
import { ReportGenerateIdRequest } from '../src/resource/report/report-generate-id-request';
import { ReportGetObjectRequest } from '../src/resource/report/report-get-object-request';
import { ReportGetRequest } from '../src/resource/report/report-get-request';
import { ReportPutObjectRequest } from '../src/resource/report/report-put-object-request';
import { ReportResponse } from '../src/resource/report/report-response';
import { ReportSearchRequest } from '../src/resource/report/report-search-request';
import { ReportUpdateRequest } from '../src/resource/report/report-update-request';

dotenv.config({ path: '.env', override: true });

const {
  RTC4J_TEST_PROJECT_ID,
  RTC4J_TEST_WORKSPACE_ID,
  RTC4J_TEST_ACCESS_ID,
  RTC4J_TEST_ENDPOINT,
  RTC4J_TEST_REPORT_ID,
  RTC4J_TEST_ACCESS_KEY,
  RTC4J_TEST_OBJECT_ID,
} = process.env;

const connector = RapidTableConnector.builder()
  .accessId(RTC4J_TEST_ACCESS_ID || '')
  .accessKey(RTC4J_TEST_ACCESS_KEY || '')
  .endpoint(RTC4J_TEST_ENDPOINT || '')
  // !CAUTION! Debugging in the local environment.
  .secure(false)
  .build();

//#region drive
test('drive search', async () => {
  const request = DriveSearchRequest.builder()
    .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
    .componentType(DriveComponentType.DRIVE)
    .page(0)
    .size(15)
    .build();
  try {
    const results = await connector.search(request, DriveResponse.of);
    expect(results.length).toBe(3);
    console.log('results', results[0].id);
  } catch (ex) {
    console.error(ex);
  }
});

test('drive count', async () => {
  const request = DriveCountRequest.builder()
    .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
    .componentType(DriveComponentType.REPORT)
    .query('pdf')
    .build();
  try {
    const results = await connector.count(request);
    expect(results).toBe(12);
  } catch (ex) {
    console.error(ex);
  }
});

test('drive get', async () => {
  const metadataRequest = DriveGetMetadataRequest.builder()
    .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
    .objectId(RTC4J_TEST_OBJECT_ID || '')
    .build();
  console.log('RTC4J_TEST_OBJECT_ID', RTC4J_TEST_OBJECT_ID);
  try {
    const metadata = await connector.get(metadataRequest, DriveResponse.of);
    // download
    const objectRequest = DriveGetObjectRequest.builder()
      .path(metadata.path)
      .build();
    const asset = await connector.getObject(objectRequest);
    writeAsset(asset);
  } catch (ex) {
    console.error(ex);
  }
});

test('drive put', async () => {
  try {
    const buffer = await fs.readFileSync('out/icon.jpg');
    const request = DrivePutObjectRequest.builder()
      .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
      .append(buffer, 'root/example.jpg')
      .build();
    const path = await connector.putObject(request);
    console.log('path', path);
  } catch (ex) {
    console.error(ex);
  }
});
//#endregion

//#region project

test('create project', async () => {
  let projectId = '';
  let coverPath = '';
  const projectName = 'sdk-project13';
  {
    const request = ProjectGenerateIdRequest.builder()
      .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
      .build();
    projectId = await connector.generateId(request);
    console.log('Project generateId = ' + projectId);
  }
  {
    const buffer = await fs.readFileSync('out/icon.jpg');
    const request = ProjectPutCoverRequest.builder()
      .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
      .projectId(projectId)
      .append(buffer, 'icon.jpg')
      .build();
    coverPath = await connector.putObject(request);
  }
  {
    const project = new ProjectRequestModel(
      projectId,
      projectName,
      'make project with rtc4js',
      '#12a4dd',
      coverPath,
      'ja',
    );
    const request = ProjectCreateRequest.builder()
      .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
      .request(project)
      .build();

    const response = await connector.create(request, ProjectResponse.of);
    console.log('Report putObject = ' + response);
  }
});

test('get schema', async () => {
  const request = SchemaGetRequest.builder()
    .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
    .projectId(RTC4J_TEST_PROJECT_ID || '')
    .build();
  try {
    const results = await connector.get<SchemaField[]>(request);
    expect(results.length).toBe(16);
    console.log(results);
  } catch (ex) {
    console.error(ex);
  }
});

//#endregion

//#region report
test('report search', async () => {
  const request = ReportSearchRequest.builder()
    .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
    .projectId(RTC4J_TEST_PROJECT_ID || '')
    .page(0)
    .size(15)
    .build();
  try {
    const results = await connector.search(request, ReportResponse.of);
    expect(results.length).toBe(15);
    console.log(results);
  } catch (ex) {
    console.error(ex);
  }
});

test('report count', async () => {
  const request = ReportCountRequest.builder()
    .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
    .projectId(RTC4J_TEST_PROJECT_ID || '')
    .build();
  try {
    const results = await connector.count(request);
    expect(results).toBe(300);
  } catch (ex) {
    console.error(ex);
  }
});

test('report bulk search', async () => {
  const request = ReportBulkSearchRequest.builder()
    .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
    .projectIds('5UbIwF_JiJT2cKgBmNYhn', 'q3hoeHa5znQ7vS4dI7RjV')
    .query('京浜')
    .page(0)
    .size(15)
    .build();
  try {
    const results = await connector.search(request, ReportResponse.of);
    console.log(results);
    expect(results.length).toBe(4);
  } catch (ex) {
    console.error(ex);
  }
});

test('report bulk count', async () => {
  const request = ReportBulkCountRequest.builder()
    .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
    .projectIds('5UbIwF_JiJT2cKgBmNYhn', 'q3hoeHa5znQ7vS4dI7RjV')
    .query('工場')
    .build();
  try {
    const results = await connector.count(request);
    expect(results).toBe(6);
  } catch (ex) {
    console.error(ex);
  }
});

test('report aggregate', async () => {
  const request = ReportAggregateValueRequest.builder()
    .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
    .projectIds('3kyUo6wpJ20JKZeIYNUA4')
    .fieldId('lsN_83K6je')
    .build();
  try {
    const results = await connector.bulkGet(request, AggregateValueResponse.of);
    console.log(results);
    expect(results.length).toBe(61);
  } catch (ex) {
    console.error(ex);
  }
});

test('report get', async () => {
  const request = ReportGetRequest.builder()
    .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
    .projectId(RTC4J_TEST_PROJECT_ID || '')
    .reportId(RTC4J_TEST_REPORT_ID || '')
    .build();
  try {
    const report = await connector.get(request, ReportResponse.of);
    console.log('report', report);
    // FIXME: Specify the field id you want to inspect
    const testImageFieldId = 'img';
    const images = report.getFieldAsList(testImageFieldId);
    expect(images).not.toBeNull();
    expect(images.length).toBe(1);

    // download
    const objectRequest = ReportGetObjectRequest.builder()
      .target(images[0])
      .build();
    const blob = await connector.getObject(objectRequest);
    console.log('data', blob.fileName, blob.contentType, blob.contentLength);
    writeAsset(blob);
  } catch (ex) {
    console.error(ex);
  }
});

test('report bulkGet', async () => {
  const request = ReportBulkGetRequest.builder()
    .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
    .projectId(RTC4J_TEST_PROJECT_ID || '')
    .ids(
      'oUtcVNgmpjH4fSZ0cyI_V',
      '1dVD4o0PH72Qsjr4L_t4c',
      'ECX-4XlDYMwu1dXe7wD-d',
    )
    .build();
  const report = await connector.bulkGet(request, ReportResponse.of);
  console.log('report', report);
});

test('report generateId', async () => {
  const requestBuilder = ReportGenerateIdRequest.builder()
    .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
    .projectId(RTC4J_TEST_PROJECT_ID || '')
    .build();
  const response = await connector.generateId(requestBuilder);
  // FIXME
  console.log('Report generateId = ' + response);
});

test('report create', async () => {
  const requestBuilder = ReportCreateRequest.builder()
    .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
    .projectId(RTC4J_TEST_PROJECT_ID || '')
    .append(makeDummyFields())
    .build();
  const response = await connector.create(requestBuilder, ReportResponse.of);
  // FIXME
  console.log(response);
});

test('report update', async () => {
  const request = ReportGetRequest.builder()
    .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
    .projectId(RTC4J_TEST_PROJECT_ID || '')
    .reportId(RTC4J_TEST_REPORT_ID || '')
    .build();
  const response = await connector.get(request, ReportResponse.of);

  // FIXME
  const testUpdateFieldId = 'keyword';
  response.setField(
    testUpdateFieldId,
    response.getFieldAsString(testUpdateFieldId) + '-update2',
  );

  const requestBuilder = ReportUpdateRequest.builder()
    .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
    .projectId(RTC4J_TEST_PROJECT_ID || '')
    .append(RTC4J_TEST_REPORT_ID || '', response.fields)
    .build();
  const updated = await connector.update(requestBuilder, ReportResponse.of);
  // FIXME
  console.log('Report update = ' + updated);
});

test('project import package', async () => {
  const buffer = await fs.readFileSync('out/全フィールド.zip');
  const request = ProjectImportPackageRequest.builder()
    .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
    .projectId(RTC4J_TEST_PROJECT_ID || '')
    .forceUseReportId(true)
    // .projectId(RTC4J_TEST_PROJECT_ID || '')
    .append(buffer)
    .build();

  const response = await connector.importPackage(request);
  expect(response.length).toBe(0);
});

test('project export package', async () => {
  const request = ProjectExportPackageRequest.builder()
    .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
    .projectId(RTC4J_TEST_PROJECT_ID || '')
    .gte('number', '5')
    .build();
  try {
    const asset = await connector.getObject(request);
    writeAsset(asset);
  } catch (ex) {
    console.error(ex);
  }
});

test('report delete', async () => {
  const request = ReportSearchRequest.builder()
    .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
    .projectId(RTC4J_TEST_PROJECT_ID || '')
    .size(100)
    .gte('number', '100')
    .build();
  const response = await connector.search(request, ReportResponse.of);
  // FIXME
  console.log('Report search = ' + response.length);
  if (response.length) {
    const ids = response.map(({ id }) => id);
    const deleteRequest = ReportDeleteRequest.builder()
      .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
      .projectId(RTC4J_TEST_PROJECT_ID || '')
      .ids(...ids)
      .build();
    await connector.delete(deleteRequest);
  }
});

test('report put object', async () => {
  const buffer = await fs.readFileSync(
    'out/image.png',
  );
  const request = ReportPutObjectRequest.builder()
    .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
    .projectId(RTC4J_TEST_PROJECT_ID || '')
    .reportId(RTC4J_TEST_REPORT_ID || '')
    .append(buffer, 'image.png')
    .build();
  const response = await connector.putObject(request);
  console.log('Report putObject = ' + response);
});

test('report delete object by ids', async () => {
  const request = ReportDeleteObjectRequest.builder()
    .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
    .projectId(RTC4J_TEST_PROJECT_ID || '')
    .reportId(RTC4J_TEST_REPORT_ID || '')
    .objectId(RTC4J_TEST_OBJECT_ID || '')
    .build();
  await connector.delete(request);
});

test('report delete object by path', async () => {
  // FIXME
  const target = '***********************';
  const request = ReportDeleteObjectRequest.builder().target(target).build();
  await connector.delete(request);
});
// #endregion

function writeAsset(object: GetObjectResponse): void {
  const rootDir = 'out';
  if (!fs.existsSync(rootDir)) {
    fs.mkdirSync(rootDir, { recursive: true });
  }

  const assetPath = rootDir + '/' + object.fileName;

  fs.writeFileSync(assetPath, object.data, 'binary');
}

function makeDummyFields(): { [key: string]: unknown } {
  const fields: { [key: string]: unknown } = {};
  // Number
  fields['number'] = 4;
  // Text
  fields['keyword'] = 'キーワード 4';
  // Text
  fields['longtext'] = 'アルバートサウルス 4';
  // Text
  fields['richtext'] = '<p><strong>アルバートサウルス</strong></p>';
  // DateTime
  fields['datetime'] = new Date(2023, 5, 10, 12, 34, 45);
  // Date
  fields['date'] = new Date(2023, 6, 11, 12, 34, 45);
  // Time
  fields['time'] = new Date(2023, 5, 10, 13, 54, 2);
  // Check
  fields['check'] = ['Check 2', 'Check 1'];
  // Select
  fields['select'] = 'Select 1';
  // Radio
  fields['radio'] = 'Radio 3';
  // Check (Mode ref)
  fields['fTbsepeysl'] = ['1', '3', '4'];
  // Select (Mode ref)
  fields['uzi0RIu_wr'] = '2';
  // Radio (Mode ref)
  fields['nvbeWbPRAU'] = '3';
  // Tags
  fields['tag'] = ['oBJPEiomapd2MQNolCi8F'];
  // Phone number
  fields['phone'] = '090-1234-7890';
  // E-Mail
  fields['mail'] = 'mail@example.com';
  // Zip code
  fields['zipCode'] = '123-4567';
  // File path (image)
  fields['img'] = [
    'reports/EMJq7jEa2p6Ahhjg6VfG3/evn3MoQs0theV6tVDq4mS/LEFvlEacefPPcvav6O0ry/objects/kkA5JEFi4y-BKiH9wuV_4?v=1779588340849',
  ];
  // File path
  fields['file'] = [
    'reports/EMJq7jEa2p6Ahhjg6VfG3/evn3MoQs0theV6tVDq4mS/_A6iyqT6cJmZyCDiDm2lA/objects/3zOIR5vm0FuFoP2LmXxpO?v=1779588341437',
  ];
  // User Ids
  fields['user'] = ['dKpjWr7f95Uz4zla91-AH', 'eLpBldxfwl06AWoJPsGJM'];
  // Projects
  fields['0sTYzI2IPA'] = ['wz2Y1nlIujD6LBnVJPwrn'];
  // Rate
  fields['rate'] = 5;
  // Url
  fields['url'] = 'https://example.com';
  return fields;
}
