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
import { Blob } from 'buffer';

import { expect, jest, test } from '@jest/globals';
import { RapidTableConnector } from '../src/rapid-table-connector';
import { DriveGetMetadataRequest } from '../src/resource/drive/drive-get-metadata-request';
import { DriveGetObjectRequest } from '../src/resource/drive/drive-get-object-request';
import { DriveResponse } from '../src/resource/drive/drive-response';
import { GetObjectResponse } from '../src/resource/get-object-response';
import { ReportCountRequest } from '../src/resource/report/report-count-request';
import { ReportCreateRequest } from '../src/resource/report/report-create-request';
import { ReportDeleteRequest } from '../src/resource/report/report-delete-request';
import { ReportGenerateIdRequest } from '../src/resource/report/report-generate-id-request';
import { ReportGetObjectRequest } from '../src/resource/report/report-get-object-request';
import { ProjectGenerateIdRequest } from '../src/resource/project/project-generate-id-request';
import { ProjectCreateRequest } from '../src/resource/project/project-create-request';
import { ProjectRequestModel } from '../src/resource/project/project-request-model';
import { ProjectResponse } from '../src/resource/project/project-response';
import { ProjectPutCoverRequest } from '../src/resource/project/project-put-cover-request';
import { SchemaGetRequest } from '../src/resource/project/schema-get-request';
import { SchemaField } from '../src/resource/project/schema-field';
import { ReportGetRequest } from '../src/resource/report/report-get-request';
import { ReportBulkGetRequest } from '../src/resource/report/report-bulk-get-request';
import { ReportPutObjectRequest } from '../src/resource/report/report-put-object-request';
import { ReportResponse } from '../src/resource/report/report-response';
import { ReportAggregateValueRequest } from '../src/resource/report/report-aggregate-value-request';
import { AggregateValueResponse } from '../src/resource/report/aggregate-value-response';
import { ReportBulkSearchRequest } from '../src/resource/report/report-bulk-search-request';
import { ReportBulkCountRequest } from '../src/resource/report/report-bulk-count-request';
import { ReportSearchRequest } from '../src/resource/report/report-search-request';
import { ReportUpdateRequest } from '../src/resource/report/report-update-request';
import { ReportDeleteObjectRequest } from '../src/resource/report/report-delete-object-request';
import { DriveSearchRequest } from '../src/resource/drive/drive-search-request';
import { DriveComponentType } from '../src/resource/drive/drive-component-type';
import { DriveCountRequest } from '../src/resource/drive/drive-count-request';

dotenv.config();

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

// //#region drive
// test('drive search', async () => {
//     const request = DriveSearchRequest.builder()
//         .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
//         .componentType(DriveComponentType.DRIVE)
//         .page(0)
//         .size(15)
//         .build();
//     try {
//         const results = await connector.search(request, DriveResponse.of);
//         expect(results.length)
//             .toBe(6);
//         console.log('results', results[0].id);
//     } catch (ex) {
//         console.error(ex);
//     }
// });

// test('drive count', async () => {
//     const request = DriveCountRequest.builder()
//         .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
//         .componentType(DriveComponentType.REPORT)
//         .query('png')
//         .build();
//     try {
//         const results = await connector.count(request);
//         expect(results)
//             .toBe(19);
//     } catch (ex) {
//         console.error(ex);
//     }
// });

// test('drive get', async () => {
//     const metadataRequest = DriveGetMetadataRequest.builder()
//         .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
//         .objectId(RTC4J_TEST_OBJECT_ID || '')
//         .build();
//     try {
//         const metadata = await connector.get(metadataRequest, DriveResponse.of);

//         // download
//         const objectRequest = DriveGetObjectRequest.builder()
//             .path(metadata.path)
//             .build();
//         const asset = await connector.getObject(objectRequest);
//         writeAsset(asset);
//     } catch (ex) {
//         console.error(ex);
//     }
// });
// //#endregion

// //#region project

// test('create project', async () => {
//   let projectId = '';
//   let coverPath = '';
//   const projectName = 'sdk-project11';
//   {
//     const request = ProjectGenerateIdRequest.builder()
//       .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
//       .build();
//     projectId = await connector.generateId(request);
//     console.log('Project generateId = ' + projectId);
//   }
//   {
//     const buffer = await fs.readFileSync('out/icon.jpg');
//     const request = ProjectPutCoverRequest.builder()
//       .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
//       .projectId(projectId)
//       .append(buffer, 'icon.jpg')
//       .build();
//     coverPath = await connector.putObject(request);
//   }
//   {
//     const project = new ProjectRequestModel(
//       projectId,
//       projectName,
//       'make project with rtc4js',
//       '#ddcc12',
//       coverPath,
//       'ja'
//     );
//     const request = ProjectCreateRequest.builder()
//       .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
//       .request(project)
//       .build();

//     const response = await connector.create(request, ProjectResponse.of);
//     console.log('Report putObject = ' + response);
//   }
// });

// test('get schema', async () => {
//     const request = SchemaGetRequest.builder()
//         .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
//         .projectId(RTC4J_TEST_PROJECT_ID || '')
//         .build();
//     try {
//         const results = await connector.get<SchemaField[]>(request);
//         expect(results.length)
//             .toBe(8);
//     } catch (ex) {
//         console.error(ex);
//     }
// });

// //#endregion

// //#region report
// test('report search', async () => {
//     const request = ReportSearchRequest.builder()
//         .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
//         .projectId(RTC4J_TEST_PROJECT_ID || '')
//         .page(0)
//         .size(15)
//         .build();
//     try {
//         const results = await connector.search(request, ReportResponse.of);
//         expect(results.length)
//             .toBe(3);
//     } catch (ex) {
//         console.error(ex);
//     }
// });

// test('report count', async () => {
//     const request = ReportCountRequest.builder()
//         .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
//         .projectId(RTC4J_TEST_PROJECT_ID || '')
//         .build();
//     try {
//         const results = await connector.count(request);
//         expect(results)
//             .toBe(44);
//     } catch (ex) {
//         console.error(ex);
//     }
// });

// test('report bulk search', async () => {
//     const request = ReportBulkSearchRequest.builder()
//         .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
//         .projectIds("XJ-sbocpVOsKlmaUyzJfT", "cobukD_dO41NcU3wOBKwY")
//         .query("金融２２")
//         .page(0)
//         .size(15)
//         .build();
//     try {
//         const results = await connector.search(request, ReportResponse.of);
//         expect(results.length)
//             .toBe(15);
//     } catch (ex) {
//         console.error(ex);
//     }
// });

// test('report bulk count', async () => {
//     const request = ReportBulkCountRequest.builder()
//         .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
//         .projectIds("XJ-sbocpVOsKlmaUyzJfT", "cobukD_dO41NcU3wOBKwY")
//         .query("金融２２")
//         .build();
//     try {
//         const results = await connector.count(request);
//         expect(results)
//             .toBe(40);
//     } catch (ex) {
//         console.error(ex);
//     }
// });

// test('report aggregate', async () => {
//     const request = ReportAggregateValueRequest.builder()
//         .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
//         .projectIds("cobukD_dO41NcU3wOBKwY", "50Tbhd8aSfQN9_NgUuHGU")
//         .fieldId("Hn0Y4fj4G5")
//         .build();
//     try {
//         const results = await connector.bulkGet(request, AggregateValueResponse.of);
//         console.log(results);
//         expect(results.length)
//             .toBe(42);
//     } catch (ex) {
//         console.error(ex);
//     }
// });

// test('report get', async () => {
//     const request = ReportGetRequest.builder()
//         .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
//         .projectId(RTC4J_TEST_PROJECT_ID || '')
//         .reportId(RTC4J_TEST_REPORT_ID || '')
//         .build();
//     try {
//         const report = await connector.get(request, ReportResponse.of);
//         console.log('report', report);
//         // FIXME: Specify the field id you want to inspect
//         const testImageFieldId = 'BAJNu8NgYi';
//         const images = report.getFieldAsList(testImageFieldId);
//         expect(images).not.toBeNull();
//         expect(images.length).toBe(1);

//         // download
//         const objectRequest = ReportGetObjectRequest.builder()
//             .target(images[0])
//             .build();
//         const blob = await connector.getObject(objectRequest);
//         console.log('data', blob.fileName, blob.contentType, blob.contentLength);
//         writeAsset(blob);
//     } catch (ex) {
//         console.error(ex);
//     }
// });

// test('report bulkGet', async () => {
//     const request = ReportBulkGetRequest.builder()
//         .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
//         .projectId(RTC4J_TEST_PROJECT_ID || '')
//         .ids("2dKW3t_rRCVsa782EPe0D", "KDsPyA8nkqCQ8IxjnjI4D", "n_Ujd4xm3cS-MayoWqd5G")
//         .build();
//     const report = await connector.bulkGet(request, ReportResponse.of);
//     console.log('report', report);
// });

// test('report generateId', async () => {
//     const requestBuilder = ReportGenerateIdRequest.builder()
//         .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
//         .projectId(RTC4J_TEST_PROJECT_ID || '')
//         .build();
//     const response = await connector.generateId(requestBuilder);
//     // FIXME
//     console.log('Report generateId = ' + response);
// });

// test('report create', async () => {
//     const requestBuilder = ReportCreateRequest.builder()
//         .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
//         .projectId(RTC4J_TEST_PROJECT_ID || '')
//         .append(makeDummyFields())
//         .build();
//     const response = await connector.create(requestBuilder, ReportResponse.of);
//     // FIXME
//     console.log('Report create = ' + response);
// });

// test('report update', async () => {
//     const request = ReportGetRequest.builder()
//         .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
//         .projectId(RTC4J_TEST_PROJECT_ID || '')
//         .reportId(RTC4J_TEST_REPORT_ID || '')
//         .build();
//     const response = await connector.get(request, ReportResponse.of);

//     // FIXME
//     const testUpdateFieldId = 'B97LjXOPr7';
//     response.setField(testUpdateFieldId, response.getFieldAsString(testUpdateFieldId) + '-update');

//     const requestBuilder = ReportUpdateRequest.builder()
//         .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
//         .projectId(RTC4J_TEST_PROJECT_ID || '')
//         .append(RTC4J_TEST_REPORT_ID || '', response.fields)
//         .build();
//     const updated = await connector.update(requestBuilder, ReportResponse.of);
//     // FIXME
//     console.log('Report update = ' + updated);
// });

// test('report delete', async () => {
//     const request = ReportSearchRequest.builder()
//         .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
//         .projectId(RTC4J_TEST_PROJECT_ID || '')
//         .size(100)
//         .gte('CssVTEBWnK', '2023-05-09T00:00:00.000Z')
//         .build();
//     const response = await connector.search(request, ReportResponse.of);
//     // FIXME
//     console.log('Report search = ' + response.length);
//     if (response.length) {
//         const ids = response.map(({ id }) => id);
//         const deleteRequest = ReportDeleteRequest.builder()
//             .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
//             .projectId(RTC4J_TEST_PROJECT_ID || '')
//             .ids(...ids)
//             .build();
//         await connector.delete(deleteRequest);
//     }
// });

// test('report put object', async () => {
//     const buffer = await fs.readFileSync('out/AntarcticPenguinChicks_JA-JP8530205289_1366x768.jpg');
//     const request = ReportPutObjectRequest.builder()
//         .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
//         .projectId(RTC4J_TEST_PROJECT_ID || '')
//         .reportId(RTC4J_TEST_REPORT_ID || '')
//         .append(buffer, 'AntarcticPenguinChicks_JA-JP8530205289_1366x768.jpg')
//         .build();
//     const response = await connector.putObject(request);
//     console.log('Report putObject = ' + response);
// });

// test('report delete object by ids', async () => {
//     const request = ReportDeleteObjectRequest.builder()
//         .workspaceId(RTC4J_TEST_WORKSPACE_ID || '')
//         .projectId(RTC4J_TEST_PROJECT_ID || '')
//         .reportId(RTC4J_TEST_REPORT_ID || '')
//         .objectId(RTC4J_TEST_OBJECT_ID || '')
//         .build();
//     await connector.delete(request);
// });

// test('report delete object by path', async () => {
//     // FIXME
//     const target = '***********************';
//     const request = ReportDeleteObjectRequest.builder()
//         .target(target)
//         .build();
//     await connector.delete(request);
// });
//#endregion

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
  fields['bkdTVr1J51'] = 4;
  // Text
  fields['B97LjXOPr7'] = 'キーワード 3';
  // Text
  fields['AIxU8hOLfM'] = 'アルバートサウルス 3';
  // Text
  fields['WR-2A8cFUA'] = '<p><strong>アルバートサウルス</strong></p>';
  // DateTime
  fields['CssVTEBWnK'] = new Date(2023, 5, 10, 12, 34, 45);
  // Date
  fields['fgW1DnIC38'] = new Date(2023, 6, 11, 12, 34, 45);
  // Time
  fields['auuTZ-huou'] = new Date(2023, 5, 10, 13, 54, 2);
  // Check
  fields['PxetCrETIb'] = ['Check 2', 'Check 1'];
  // Select
  fields['fQNlK73ey3'] = 'Select 1';
  // Radio
  fields['6OY_pjJU9k'] = 'Radio 3';
  // Check (Mode ref)
  fields['ReIynbHDvl'] = ['36', '33', '50'];
  // Select (Mode ref)
  fields['RQdGg98STz'] = '41';
  // Radio (Mode ref)
  fields['IjoVtmbIeu'] = '44';
  // Tags
  fields['SC3K_VdiqA'] = ['U2beeXU9DeFI3T3mXn129', 'isEMBD-LG72_An2U0Njs5'];
  // Phone number
  fields['N8Eey-6PKW'] = '090-1234-7890';
  // E-Mail
  fields['bSqX1XakUG'] = 'mail@example.com';
  // Zip code
  fields['FPrXVrNog2'] = '123-4567';
  // File path (image)
  fields['BAJNu8NgYi'] = [
    'reports/qZle8BmYD8C5GS_94pUIF/WfNzIMYphSkgBiEzqz049/NT9QWWp39m7apQluSyalc/objects/5UKFdFAnzSuUTPv_m7DY8?v=1676013531349',
  ];
  // File path
  fields['JX_ce48L7h'] = [
    'reports/qZle8BmYD8C5GS_94pUIF/WfNzIMYphSkgBiEzqz049/NT9QWWp39m7apQluSyalc/objects/1Yn3PBhL_dbdCrRgtYrPH?v=1674895052825',
  ];
  // User Ids
  fields['X_ZZVIQKqz'] = ['dKpjWr7f95Uz4zla91-AH', 'eLpBldxfwl06AWoJPsGJM'];
  // Projects
  fields['0sTYzI2IPA'] = ['wz2Y1nlIujD6LBnVJPwrn'];
  // Rate
  fields['Tl4IGjgYFv'] = 5;
  // Url
  fields['zZiNQEjJ5F'] = 'https://example.com';
  return fields;
}
