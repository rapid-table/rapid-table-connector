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

import { handleError, HttpError } from './http/http-error';
import { HttpMethod } from './http/http-method';
import { HttpResponseType, resolveAccept } from './http/http-request-type';
import { HttpResponse, resolveHttpResponse } from './http/http-response';
import { IDeleteRequest } from './resource/delete-request.interface';
import { IGenerateIdRequest } from './resource/generate-id-request.interface';
import { GetObjectResponse } from './resource/get-object-response';
import { IImportPackageRequest } from './resource/import-package-request.interface';
import { PathConfig } from './resource/path-config';
import { IPutObjectRequest } from './resource/put-object-request.interface';
import { ImportFailedReport } from './resource/report/import-failed-report';
import { IRequest } from './resource/request.interface';

const AUTHORIZATION_HEADER = 'authorization';

export class RapidTableConnector {
  private accessId: string;
  private accessKey: string;
  private credentials: Credentials;
  private baseURL: string;

  constructor(
    accessId: string,
    accessKey: string,
    host: string,
    secure: boolean,
    credentials?: Credentials,
  ) {
    this.accessId = accessId;
    this.accessKey = accessKey;
    const schema = secure ? 'https' : 'http';
    this.baseURL = `${schema}://${host}`;
    this.credentials = credentials || Credentials.empty();
  }

  public async search<T>(
    request: IRequest,
    instanceFn?: (arg: T) => T,
  ): Promise<T[]> {
    const postscript = ({ data }: HttpResponse<T[]>): T[] =>
      instanceFn ? data.map((item) => instanceFn(item)) : data;

    return await this.getRequest('search', postscript, request);
  }

  public async count(request: IRequest): Promise<number> {
    const postscript = ({ data }: HttpResponse<number>) => data;

    return await this.getRequest('count', postscript, request);
  }

  public async get<T>(
    request: IRequest,
    instanceFn?: (arg: T) => T,
  ): Promise<T> {
    const postscript = ({ data }: HttpResponse<T>) =>
      instanceFn ? instanceFn(data) : data;

    return await this.getRequest('get', postscript, request);
  }

  public async bulkGet<T>(
    request: IRequest,
    instanceFn?: (arg: T) => T,
  ): Promise<T[]> {
    const postscript = ({ data }: HttpResponse<T[]>) =>
      instanceFn ? data.map((item) => instanceFn(item)) : data;

    return await this.getRequest('bulkGet', postscript, request);
  }

  public async getObject(request: IRequest): Promise<GetObjectResponse> {
    const postscript = (res: HttpResponse<ArrayBuffer>): GetObjectResponse => {
      const fileName = decodeURIComponent(res.headers.get('etag') || '');
      const contentType = '' + res.headers.get('content-type') || '';
      const contentLength = +(res.headers.get('content-length') || NaN);
      const data = Buffer.from(res.data);
      return new GetObjectResponse(fileName, contentType, contentLength, data);
    };

    return await this.getRequest(
      'getObject',
      postscript,
      request,
      'arraybuffer',
    );
  }

  public async generateId(request: IGenerateIdRequest): Promise<string> {
    const postscript = ({ data }: HttpResponse<string>) => data;

    return await this.getRequest('generateId', postscript, request, 'text');
  }

  public async create<T>(
    request: IRequest,
    instanceFn?: (arg: T) => T,
  ): Promise<T> {
    const postscript = ({ data }: HttpResponse<T>) =>
      instanceFn ? instanceFn(data) : data;

    return await this.postRequest('create', postscript, request);
  }

  public async update<T>(
    request: IRequest,
    instanceFn?: (arg: T) => T,
  ): Promise<T> {
    const postscript = ({ data }: HttpResponse<T>) =>
      instanceFn ? instanceFn(data) : data;

    return await this.putRequest('update', postscript, request);
  }

  public async delete(request: IDeleteRequest): Promise<void> {
    return await this.deleteRequest('delete', request);
  }

  public async putObject(request: IPutObjectRequest): Promise<string> {
    const postscript = ({ data }: HttpResponse<string>) => data;

    return await this.putRequest('putObject', postscript, request, 'text');
  }

  public async importPackage(
    request: IImportPackageRequest,
  ): Promise<ImportFailedReport[]> {
    const postscript = ({ data }: HttpResponse<ImportFailedReport[]>) => data;

    return await this.postRequest('importPackage', postscript, request);
  }

  public async permission(): Promise<Credentials> {
    if (!this.accessId || !this.accessKey) {
      throw new Error(`Permission failed: No access key or ID.`);
    }
    try {
      const request: IRequest = {
        getPath: () => PathConfig.ROOT + PathConfig.PERMISSIONS,
        getQuery: () => ({}),
        getBody: () =>
          JSON.stringify({
            email: this.accessId,
            key: this.accessKey,
          }),
      };

      const response = await this.exchange('POST', request);
      const token = response.headers.get(AUTHORIZATION_HEADER) || '';
      return Credentials.approve(token);
    } catch (error) {
      throw handleError('Permission', error);
    }
  }

  //#region Internal Http Client Method
  private async getRequest<T, R>(
    invokerName: string,
    postscript: (args: HttpResponse<T>) => R,
    request:
      | IRequest
      | IDeleteRequest
      | IGenerateIdRequest
      | IImportPackageRequest
      | IPutObjectRequest,
    responseType: HttpResponseType = 'json',
  ): Promise<R> {
    try {
      await this.ensurePermission();
      const res = await this.exchange<T>('GET', request, responseType);
      return postscript(res);
    } catch (error) {
      throw handleError(invokerName, error);
    }
  }

  private async postRequest<T, R>(
    invokerName: string,
    postscript: (args: HttpResponse<T>) => R,
    request:
      | IRequest
      | IDeleteRequest
      | IGenerateIdRequest
      | IImportPackageRequest
      | IPutObjectRequest,
    responseType: HttpResponseType = 'json',
  ): Promise<R> {
    try {
      await this.ensurePermission();
      const res = await this.exchange<T>('POST', request, responseType);
      return postscript(res);
    } catch (error) {
      throw handleError(invokerName, error);
    }
  }

  private async putRequest<T, R>(
    invokerName: string,
    postscript: (args: HttpResponse<T>) => R,
    request:
      | IRequest
      | IDeleteRequest
      | IGenerateIdRequest
      | IImportPackageRequest
      | IPutObjectRequest,
    responseType: HttpResponseType = 'json',
  ): Promise<R> {
    try {
      await this.ensurePermission();
      const res = await this.exchange<T>('PUT', request, responseType);
      return postscript(res);
    } catch (error) {
      throw handleError(invokerName, error);
    }
  }

  private async deleteRequest(
    invokerName: string,
    request:
      | IRequest
      | IDeleteRequest
      | IGenerateIdRequest
      | IImportPackageRequest
      | IPutObjectRequest,
  ): Promise<void> {
    try {
      await this.ensurePermission();
      await this.exchange('DELETE', request);
    } catch (error) {
      throw handleError(invokerName, error);
    }
  }

  private async exchange<T>(
    method: HttpMethod,
    request:
      | IRequest
      | IDeleteRequest
      | IGenerateIdRequest
      | IImportPackageRequest
      | IPutObjectRequest,
    responseType: HttpResponseType = 'json',
    timeoutMs: number = 0,
  ): Promise<HttpResponse<T>> {
    if (!request.getPath()) {
      throw new Error(`IllegalArgumentException`);
    }

    const url = new URL(request.getPath(), this.baseURL);

    // -------- Query param --------
    const query = 'getQuery' in request ? request.getQuery() : undefined;
    if (query) {
      Object.entries(query).forEach(([k, v]) => {
        if (v != null) url.searchParams.append(k, String(v));
      });
    }

    // -------- Body --------
    const body: BodyInit | undefined = (() => {
      if ('getFormData' in request && !!request.getFormData()) {
        return request.getFormData() as unknown as BodyInit;
      } else if ('getBody' in request && !!request.getBody()) {
        const rawBody = request.getBody() as unknown;
        if (
          typeof rawBody === 'string' ||
          rawBody instanceof Blob ||
          rawBody instanceof URLSearchParams ||
          rawBody instanceof ArrayBuffer
        ) {
          return rawBody;
        } else if (rawBody != null) {
          return JSON.stringify(rawBody);
        }
      }
      return undefined;
    })();

    // -------- Headers --------
    const headers = new Headers();
    headers.set('Accept', resolveAccept(responseType));
    headers.set('X-Requested-With', 'XMLHttpRequest');
    if (typeof window === 'undefined') {
      headers.set('User-Agent', 'RapidTableClient/1.0');
    }
    if (this.credentials.token) {
      headers.set(AUTHORIZATION_HEADER, this.credentials.token);
    }
    if ('getBody' in request && !!request.getBody()) {
      headers.set('Content-Type', 'application/json; charset=utf-8');
    }

    // -------- Timeout --------
    let controller: AbortController | undefined;
    let timeoutId: any;
    if (timeoutMs && timeoutMs > 0) {
      controller = new AbortController();
      timeoutId = setTimeout(() => controller!.abort(), timeoutMs);
    }

    try {
      const response = await fetch(url.toString(), {
        method,
        headers,
        body,
        signal: controller?.signal,
        credentials: 'include',
      });

      return await resolveHttpResponse(response, responseType);
    } catch (err: any) {
      if (err.name === 'AbortError') {
        throw new Error(`Timeout (${timeoutMs}ms)`);
      }
      if (err instanceof HttpError) {
        throw err;
      }
      throw new Error(`Network error: ${err.message}`);
    } finally {
      if (timeoutId) {
        clearTimeout(timeoutId);
      }
    }
  }

  private async ensurePermission() {
    if (!this.credentials.isPermitted()) {
      this.credentials = await this.permission();
    }
  }
  //#endregion

  public static builder(): RapidTableConnectorBuilder {
    return new RapidTableConnectorBuilder();
  }
}

class RapidTableConnectorBuilder {
  private _accessId: string | null = null;
  private _accessKey: string | null = null;
  private _endpoint: string | null = null;
  private _secure: boolean = true;
  private _credentials?: Credentials;

  public accessId(accessId: string): RapidTableConnectorBuilder {
    this._accessId = accessId;
    return this;
  }

  public accessKey(accessKey: string): RapidTableConnectorBuilder {
    this._accessKey = accessKey;
    return this;
  }

  public endpoint(endpoint: string): RapidTableConnectorBuilder {
    this._endpoint = endpoint;
    return this;
  }

  public secure(secure: boolean): RapidTableConnectorBuilder {
    this._secure = secure;
    return this;
  }

  public credentials(credentials: Credentials): RapidTableConnectorBuilder {
    this._credentials = credentials;
    return this;
  }

  public build(): RapidTableConnector {
    if (!this._credentials) {
      if (!this._accessId) {
        throw new Error('accessId is required');
      }
      if (!this._accessKey) {
        throw new Error('accessKey is required');
      }
    }
    if (!this._endpoint) {
      throw new Error('endpoint is required');
    }
    return new RapidTableConnector(
      this._accessId || '',
      this._accessKey || '',
      this._endpoint,
      this._secure,
      this._credentials,
    );
  }
}

export class Credentials {
  constructor(
    public token: string | null,
    public approvedAt: number,
  ) {}

  isPermitted(): boolean {
    if (!this.token) {
      return false;
    }
    const diff = (Date.now() - this.approvedAt) / (1000 * 60);
    return diff <= 50;
  }

  static empty(): Credentials {
    return new Credentials(null, 0);
  }

  static approve(token: string): Credentials {
    return new Credentials(token, Date.now());
  }
}
