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

export class ReportBulkCountRequest implements IRequest {

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
    private _projectIds: string[] = [];
    private _query: string | null = null;
    private _emp: string | null = null;
    private _noemp: string | null = null;
    private _includes: string[] = [];
    private _in: string[] = [];
    private _eq: string | null = null;
    private _neq: string | null = null;
    private _lt: string | null = null;
    private _lte: string | null = null;
    private _gt: string | null = null;
    private _gte: string | null = null;
    private _term: string | null = null;

    public build(): ReportBulkCountRequest {
        const path = PathConfig.ROOT + PathConfig.WORKSPACE + `/${this._workspaceId}` +
            PathConfig.PROJECTS + PathConfig.REPORTS + PathConfig.COUNT;

        const params: { [key: string]: string | number } = {};

        if (!Array.isArray(this._projectIds) || this._projectIds.length === 0) {
            throw new Error('IllegalArgumentException');
        }
        if (this._projectIds.length > 300) {
            throw new Error('IllegalArgumentException');
        }

        params['projectIds'] = this._projectIds.join(',');

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
        if (this._eq) {
            params['eq'] = this._eq;
        }
        if (this._neq) {
            params['neq'] = this._neq;
        }
        if (this._lt) {
            params['lt'] = this._lt;
        }
        if (this._lte) {
            params['lte'] = this._lte;
        }
        if (this._gt) {
            params['gt'] = this._gt;
        }
        if (this._gte) {
            params['gte'] = this._gte;
        }
        if (this._term) {
            params['term'] = this._term;
        }

        return new ReportBulkCountRequest(path, params);
    }

    public workspaceId(workspaceId: string): Builder {
        this._workspaceId = workspaceId;
        return this;
    }

    /**
     * Write the project IDs separated by commas.
     * projectIds=project-id1,project-id2,project-id3
     */
    public projectIds(...ids: string[]): Builder {
        this._projectIds = ids;
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
        this._in = value;
        return this;
    }

    /**
     * Numeric value (equals).
     * eq=fieldId:123
     */
    public eq(fieldId: string, value: string): Builder {
        this._eq = `${fieldId}:${value}`;
        return this;
    }

    /**
     * Numeric value (not equal).
     * eq=fieldId:123
     */
    public neq(fieldId: string, value: string): Builder {
        this._neq = `${fieldId}:${value}`;
        return this;
    }

    /**
     * Numeric value (less than).
     * eq=fieldId:123
     */
    public lt(fieldId: string, value: string): Builder {
        this._lt = `${fieldId}:${value}`;
        return this;
    }

    /**
     * Numeric value (less than or equal to).
     * eq=fieldId:123
     */
    public lte(fieldId: string, value: string): Builder {
        this._lte = `${fieldId}:${value}`;
        return this;
    }

    /**
     * Numeric value (greater than).
     * eq=fieldId:123
     */
    public gt(fieldId: string, value: string): Builder {
        this._gt = `${fieldId}:${value}`;
        return this;
    }

    /**
     * Numeric value (grater than or equal to).
     * eq=fieldId:123
     */
    public gte(fieldId: string, value: string): Builder {
        this._gte = `${fieldId}:${value}`;
        return this;
    }

    /**
     * Date type period search (range).
     * yyyy-MM-dd
     */
    public term(fieldId: string, from: string, to: string): Builder {
        this._term = `${fieldId}:${from},${to}`;
        return this;
    }

    /**
     * Date type period search (from).
     * yyyy-MM-dd
     */
    public termFrom(fieldId: string, from: string): Builder {
        this._term = `${fieldId}:${from},`;
        return this;
    }

    /**
     * Date type period search (to).
     * yyyy-MM-dd
     */
    public termTo(fieldId: string, to: string): Builder {
        this._term = `${fieldId}:,${to}`;
        return this;
    }
}