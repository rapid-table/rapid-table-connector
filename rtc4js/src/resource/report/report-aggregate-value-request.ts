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

export class ReportAggregateValueRequest implements IRequest {

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
    private _ids: string[] = [];
    private _fieldId: string | null = null;

    public build(): ReportAggregateValueRequest {
        const path = PathConfig.ROOT + PathConfig.WORKSPACE + `/${this._workspaceId}` +
            PathConfig.PROJECTS + PathConfig.REPORTS + PathConfig.AGGREGATE;

        const params: { [key: string]: string | number } = {};

        if (!Array.isArray(this._ids) || this._ids.length === 0) {
            throw new Error('IllegalArgumentException');
        }
        if (this._ids.length > 300) {
            throw new Error('IllegalArgumentException');
        }

        if (!this._fieldId) {
            throw new Error('IllegalArgumentException');
        }

        params['ids'] = this._ids.join(',');
        params['fieldId'] = this._fieldId;

        return new ReportAggregateValueRequest(path, params);
    }

    public workspaceId(workspaceId: string): Builder {
        this._workspaceId = workspaceId;
        return this;
    }

    /**
     * Write the project IDs separated by commas.
     * ids=project-id1,project-id2,project-id3
     */
    public ids(...ids: string[]): Builder {
        this._ids = ids;
        return this;
    }

    /**
     * Field ID to be aggregated.
     * fieldId=fieldId
     */
    public fieldId(fieldId: string | null): Builder {
        this._fieldId = fieldId;
        return this;
    }
}