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

import { HttpError } from './http-error';
import { HttpResponseType } from './http-request-type';

export type HttpResponse<T> = {
  data: T;
  status: number;
  headers: Headers;
};

export async function resolveHttpResponse<T>(
  response: Response,
  responseType: HttpResponseType,
): Promise<HttpResponse<T>> {
  if (!response.ok) {
    const body = await safeParse(response);
    throw new HttpError(
      `HTTP error: ${response.status}`,
      response.status,
      body,
    );
  }

  const data = await (async () => {
    switch (responseType) {
      case 'arraybuffer':
        return await response.arrayBuffer();
      case 'blob':
        return await response.blob();
      case 'text':
        return await response.text();
      case 'json':
      default:
        const text = await response.text();
        return text ? JSON.parse(text) : null;
    }
  })();

  return { data, status: response.status, headers: response.headers };
}

async function safeParse(res: Response) {
  const text = await res.text();
  const contentType = res.headers.get('content-type') || '';
  if (contentType.includes('application/json')) {
    try {
      return JSON.parse(text);
    } catch {}
  }
  return text;
}
