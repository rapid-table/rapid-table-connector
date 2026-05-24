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

export function prepareFormData(
  formData: FormData | null,
  buffer: any,
  fileName: string,
): FormData {
  if (!formData) {
    formData = new FormData();
  }
  if (Buffer.isBuffer(buffer)) {
    const type = resolveMimeType(fileName);
    const blob = new Blob([new Uint8Array(buffer)], { type });
    formData.append('file', blob, fileName);
  } else {
    formData.append('file', buffer, fileName);
  }
  return formData;
}

const mimeMap: Record<string, string> = {
  png: 'image/png',
  jpg: 'image/jpeg',
  jpeg: 'image/jpeg',
  gif: 'image/gif',
  webp: 'image/webp',
  svg: 'image/svg+xml',
  tif: 'image/tiff',
  tiff: 'image/tiff',

  txt: 'text/plain',
  csv: 'text/csv',
  json: 'application/json',
  xml: 'application/xml',

  pdf: 'application/pdf',

  xls: 'application/vnd.ms-excel',
  xlsx: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',

  doc: 'application/msword',
  docx: 'application/vnd.openxmlformats-officedocument.wordprocessingml.document',

  ppt: 'application/vnd.ms-powerpoint',
  pptx: 'application/vnd.openxmlformats-officedocument.presentationml.presentation',

  zip: 'application/zip',

  mp3: 'audio/mpeg',
  mp4: 'video/mp4',
};

function resolveMimeType(fileName: string): string {
  const ext = fileName.split('.').pop()?.toLowerCase();
  return ext && mimeMap[ext] ? mimeMap[ext] : 'application/octet-stream';
}
