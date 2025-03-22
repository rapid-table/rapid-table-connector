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

import axios, { AxiosHeaders, AxiosInstance } from 'axios';
import { IDeleteRequest } from './resource/delete-request.interface';
import { IGenerateIdRequest } from './resource/generate-id-request.interface';
import { GetObjectResponse } from './resource/get-object-response';
import { IImportPackageRequest } from './resource/import-package-request.interface';
import { PathConfig } from './resource/path-config';
import { IPutObjectRequest } from './resource/put-object-request.interface';
import { ImportFailedReport } from './resource/report/import-failed-report';
import { IRequest } from './resource/request.interface';

const AUTHORIZATION_HEADER = 'authorization';
const HTTP_CONTENT_TYPE_HEADER = 'Content-Type';
const HTTP_CONTENT_TYPE_JSON_VALUE = 'application/json; charset=utf-8';

export class RapidTableConnector {
    private accessId: string;
    private accessKey: string;
    private client: AxiosInstance;
    private credentials: Credentials = Credentials.empty();

    constructor(accessId: string, accessKey: string, host: string, secure: boolean) {
        this.accessId = accessId;
        this.accessKey = accessKey;
        const schema = secure ? 'https' : 'http';
        this.client = axios.create({
            baseURL: `${schema}://${host}`,
            headers: { 'Content-Type': HTTP_CONTENT_TYPE_JSON_VALUE },
            withCredentials: true,
        });
    }

    public async search<T>(request: IRequest, instanceFn?: (arg: T) => T): Promise<T[]> {
        if (!this.credentials.isPermitted()) {
            this.credentials = await this.permission();
        }

        if (!request.getPath()) {
            return Promise.reject(`IllegalArgumentException`);
        }

        const headers = new AxiosHeaders();
        headers.set(AUTHORIZATION_HEADER, this.credentials.token);

        const params = request.getQuery();

        const res = await this.client.get<T[]>(request.getPath(), { headers, params });
        if (res.status !== 200) {
            return Promise.reject(`search failed: ${res.status}`);
        }
        if (instanceFn instanceof Function) {
            return res.data.map(item => instanceFn(item));
        }
        return res.data;
    }

    public async count(request: IRequest): Promise<number> {
        if (!this.credentials.isPermitted()) {
            this.credentials = await this.permission();
        }

        if (!request.getPath()) {
            return Promise.reject(`IllegalArgumentException`);
        }

        const headers = new AxiosHeaders();
        headers.set(AUTHORIZATION_HEADER, this.credentials.token);

        const params = request.getQuery();

        const res = await this.client.get<number>(request.getPath(), { headers, params });
        if (res.status !== 200) {
            return Promise.reject(`count failed: ${res.status}`);
        }
        return res.data;
    }

    public async get<T>(request: IRequest, instanceFn?: (arg: T) => T): Promise<T> {
        if (!this.credentials.isPermitted()) {
            this.credentials = await this.permission();
        }

        if (!request.getPath()) {
            return Promise.reject(`IllegalArgumentException`);
        }

        const headers = new AxiosHeaders();
        headers.set(AUTHORIZATION_HEADER, this.credentials.token);

        const params = request.getQuery();

        const res = await this.client.get<T>(request.getPath(), { headers, params });
        if (res.status !== 200) {
            return Promise.reject(`get failed: ${res.status}`);
        }
        if (instanceFn instanceof Function) {
            return instanceFn(res.data);
        }
        return res.data as T;
    }

    public async bulkGet<T>(request: IRequest, instanceFn?: (arg: T) => T): Promise<T[]> {
        if (!this.credentials.isPermitted()) {
            this.credentials = await this.permission();
        }

        if (!request.getPath()) {
            return Promise.reject(`IllegalArgumentException`);
        }

        const headers = new AxiosHeaders();
        headers.set(AUTHORIZATION_HEADER, this.credentials.token);

        const params = request.getQuery();

        const res = await this.client.get<T[]>(request.getPath(), { headers, params });
        if (res.status !== 200) {
            return Promise.reject(`get failed: ${res.status}`);
        }
        if (instanceFn instanceof Function) {
            return res.data.map(item => instanceFn(item));
        }
        return res.data;
    }

    public async getObject(request: IRequest): Promise<GetObjectResponse> {
        if (!this.credentials.isPermitted()) {
            this.credentials = await this.permission();
        }

        if (!request.getPath()) {
            return Promise.reject(`IllegalArgumentException`);
        }

        const headers = new AxiosHeaders();
        headers.set(AUTHORIZATION_HEADER, this.credentials.token);

        const params = request.getQuery();

        const res = await this.client.get(request.getPath(),
            {
                headers,
                responseType: 'arraybuffer',
                params
            });
        if (res.status !== 200) {
            return Promise.reject(`get failed: ${res.status}`);
        }
        const fileName = decodeURIComponent(res.headers['etag']);
        const contentType = '' + res.headers['content-type'] || '';
        const contentLength = +(res.headers['content-length'] || NaN);
        const data = Buffer.from(res.data);
        return new GetObjectResponse(fileName, contentType, contentLength, data);
    }

    public async generateId(request: IGenerateIdRequest): Promise<string> {
        if (!this.credentials.isPermitted()) {
            this.credentials = await this.permission();
        }

        if (!request.getPath()) {
            return Promise.reject(`IllegalArgumentException`);
        }

        const headers = new AxiosHeaders();
        headers.set(AUTHORIZATION_HEADER, this.credentials.token);

        const res = await this.client.get<string>(request.getPath(), { headers });
        if (res.status !== 200) {
            return Promise.reject(`get failed: ${res.status}`);
        }
        return res.data;
    }

    public async create<T>(request: IRequest, instanceFn?: (arg: T) => T): Promise<T> {
        if (!this.credentials.isPermitted()) {
            this.credentials = await this.permission();
        }

        if (!request.getPath()) {
            return Promise.reject(`IllegalArgumentException`);
        }

        const headers = new AxiosHeaders();
        headers.set(AUTHORIZATION_HEADER, this.credentials.token);

        const params = request.getQuery();

        const res = await this.client
            .post<T>(request.getPath(), request.getBody(), { headers, params });
        if (res.status !== 200) {
            return Promise.reject(`get failed: ${res.status}`);
        }
        if (instanceFn instanceof Function) {
            return instanceFn(res.data);
        }
        return res.data as T;
    }

    public async update<T>(request: IRequest, instanceFn?: (arg: T) => T): Promise<T> {
        if (!this.credentials.isPermitted()) {
            this.credentials = await this.permission();
        }

        if (!request.getPath()) {
            return Promise.reject(`IllegalArgumentException`);
        }

        const headers = new AxiosHeaders();
        headers.set(AUTHORIZATION_HEADER, this.credentials.token);

        const params = request.getQuery();

        const res = await this.client
            .put<T>(request.getPath(), request.getBody(), { headers, params });
        if (res.status !== 200) {
            return Promise.reject(`get failed: ${res.status}`);
        }
        if (instanceFn instanceof Function) {
            return instanceFn(res.data);
        }
        return res.data as T;
    }

    public async delete(request: IDeleteRequest): Promise<void> {
        if (!this.credentials.isPermitted()) {
            this.credentials = await this.permission();
        }

        if (!request.getPath()) {
            return Promise.reject(`IllegalArgumentException`);
        }

        const headers = new AxiosHeaders();
        headers.set(AUTHORIZATION_HEADER, this.credentials.token);

        const params = request.getQuery();

        const res = await this.client
            .delete(request.getPath(), { headers, params });
        if (res.status !== 200) {
            return Promise.reject(`get failed: ${res.status}`);
        }
    }

    public async putObject(request: IPutObjectRequest): Promise<string> {
        if (!this.credentials.isPermitted()) {
            this.credentials = await this.permission();
        }

        if (!request.getPath() || !request.getFormData()) {
            return Promise.reject(`IllegalArgumentException`);
        }

        const headers = new AxiosHeaders();
        headers.set(AUTHORIZATION_HEADER, this.credentials.token);
        headers.set(HTTP_CONTENT_TYPE_HEADER, 'multipart/form-data');
        const res = await this.client.put(request.getPath(), request.getFormData(), { headers });
        if (res.status !== 200) {
            return Promise.reject(`get failed: ${res.status}`);
        }
        return res.data;
    }

    public async importPackage(request: IImportPackageRequest): Promise<ImportFailedReport[]> {
        if (!this.credentials.isPermitted()) {
            this.credentials = await this.permission();
        }

        if (!request.getPath() || !request.getFormData()) {
            return Promise.reject(`IllegalArgumentException`);
        }
        const params = request.getQuery();

        const headers = new AxiosHeaders();
        headers.set(AUTHORIZATION_HEADER, this.credentials.token);
        headers.set(HTTP_CONTENT_TYPE_HEADER, 'multipart/form-data');
        const res = await this.client.post(request.getPath(), request.getFormData(), { headers, params });
        if (res.status !== 200) {
            return Promise.reject(`get failed: ${res.status}`);
        }
        return res.data;
    }

    private async permission(): Promise<Credentials> {
        const request = {
            email: this.accessId,
            key: this.accessKey
        };

        const response = await this.client
            .post(PathConfig.ROOT + PathConfig.PERMISSIONS, request);

        if (response.status !== 200) {
            return Promise.reject(`Permission failed: ${response.status}`);
        }

        const token = response.headers[AUTHORIZATION_HEADER] || '';
        return Credentials.approve(token);
    }

    public static builder(): RapidTableConnectorBuilder {
        return new RapidTableConnectorBuilder();
    }
}

class RapidTableConnectorBuilder {
    private _accessId: string | null = null;
    private _accessKey: string | null = null;
    private _endpoint: string | null = null;
    private _secure: boolean = true;

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

    public build(): RapidTableConnector {
        if (!this._accessId) {
            throw new Error('accessId is required');
        }
        if (!this._accessKey) {
            throw new Error('accessKey is required');
        }
        if (!this._endpoint) {
            throw new Error('endpoint is required');
        }
        return new RapidTableConnector(this._accessId, this._accessKey, this._endpoint, this._secure);
    }
}

class Credentials {
    constructor(
        public token: string | null,
        public approvedAt: number,
    ) { }

    isPermitted(): boolean {
        if (!this.token) {
            return false;
        }
        const diff = (this.approvedAt - Date.now()) / (1000 * 60);
        return Math.abs(diff) <= 50;
    }

    static empty(): Credentials {
        return new Credentials(null, 0);
    }

    static approve(token: string): Credentials {
        return new Credentials(token, Date.now());
    }
}
