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

import { PathConfig } from '../path-config';
import { IRequest } from '../request.interface';

export class ProjectCountRequest implements IRequest {

    constructor(private path: string, private query: { [key: string]: string | number }) {
    }

    public getPath(): string {
        return this.path;
    }

    public getQuery(): { [key: string]: string | number } {
        return this.query;
    }

    public getBody(): string | null {
        return null;
    }

    public static builder(): Builder {
        return new Builder();
    }

}

class Builder {
    private _workspaceId: string | null = null;
    private _query: string | null = null;
    private _emp: string | null = null;
    private _noemp: string | null = null;
    private _includes: string[] = [];
    private _in: string[] = [];

    public build(): ProjectCountRequest {
        const path = PathConfig.ROOT + PathConfig.WORKSPACE + `/${this._workspaceId}` +
            PathConfig.PROJECTS + PathConfig.COUNT;

        const params: { [key: string]: string | number } = {};

        if (this._query) {
            params['query'] = this._query;
        }
        if (this._emp) {
            params['emp'] = this._emp;
        }
        if (this._noemp) {
            params['noemp'] = this._noemp;
        }
        if (Array.isArray(this._includes) && this._includes.length > 0) {
            params['includes'] = this._includes.join(';');
        }
        if (Array.isArray(this._in) && this._in.length > 0) {
            params['in'] = this._in.join(';');
        }

        return new ProjectCountRequest(path, params);
    }

    public workspaceId(workspaceId: string): Builder {
        this._workspaceId = workspaceId;
        return this;
    }

    /**
     * Search string.
     * query=searchText
     */
    public query(query: string | null): Builder {
        this._query = query;
        return this;
    }

    /**
     * Word search (blank fields only).
     * emp=fieldId
     */
    public emp(fieldId: string): Builder {
        this._emp = fieldId;
        return this;
    }

    /**
     * Word search (non-blank fields only).
     * noemp=fieldId
     */
    public noemp(fieldId: string): Builder {
        this._noemp = fieldId;
        return this;
    }

    /**
     * Exact match for specific field.
     * includes=fieldId1:searchText1;fieldId2:searchText2
     * - For example, the arguments are as follows
     * 'fieldId1:searchText1', 'fieldId2:searchText2',...
     */
    public includes(...includes: string[]): Builder {
        this._includes = includes;
        return this;
    }

    /**
     * Partial match for specific field.
     * in=fieldId1:searchText1;fieldId2:searchText2
     * - For example, the arguments are as follows
     * 'fieldId1:searchText1', 'fieldId2:searchText2',...
     */
    public in(...value: string[]): Builder {
        this._in.push(...value);
        return this;
    }
}