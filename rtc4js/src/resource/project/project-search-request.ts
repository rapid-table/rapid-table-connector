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

export class ProjectSearchRequest implements IRequest {

    constructor(public path: string, public query: { [key: string]: string | number }) {
        this.path = path;
        this.query = query;
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
    private _page: number | null = null;
    private _size: number | null = null;
    private _asc: string | null = null;
    private _desc: string | null = null;
    private _query: string | null = null;
    private _emp: string | null = null;
    private _noemp: string | null = null;
    private _includes: string[] = [];
    private _in: string[] = [];

    public build(): ProjectSearchRequest {
        const path = PathConfig.ROOT + PathConfig.WORKSPACE + `/${this._workspaceId}` +
            PathConfig.PROJECTS + PathConfig.SEARCH;

        const params: { [key: string]: string | number } = {};
        if (this._page) {
            params['page'] = this._page;
        }
        if (this._size) {
            params['size'] = this._size;
        }

        if (this._asc) {
            params['asc'] = this._asc;
        }
        if (this._desc) {
            params['desc'] = this._desc;
        }
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

        return new ProjectSearchRequest(path, params);
    }

    public workspaceId(workspaceId: string): Builder {
        this._workspaceId = workspaceId;
        return this;
    }

    public page(page: number): Builder {
        this._page = page;
        return this;
    }

    public size(size: number): Builder {
        this._size = size;
        return this;
    }

    /**
     * Ascending sort field.
     * - Any
     * asc=fieldId
     * - Creation date
     * asc=cdt
     * - Update Date
     * asc=udt
     */
    public asc(fieldId: string): Builder {
        this._asc = fieldId;
        return this;
    }

    /**
     * Descending sort field.
     * - Any
     * desc=fieldId
     * - Creation date
     * desc=cdt
     * - Update Date
     * desc=udt
     */
    public desc(fieldId: string): Builder {
        this._desc = fieldId;
        return this;
    }

    /**
     * Search string.
     * query=searchText
     */
    public query(query: string): Builder {
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
        this._includes.push(...includes);
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