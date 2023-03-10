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
import { DriveComponentType } from './drive-component-type';

export class DriveCountRequest implements IRequest {
    constructor(public path: string, public query: { [key: string]: string | number }) { }

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
    private _componentType: DriveComponentType | null = null;
    private _query: string | null = null;
    private _tagIds: string[] = [];

    constructor() { }

    public build(): DriveCountRequest {
        const path = PathConfig.ROOT + PathConfig.WORKSPACE + `/${this._workspaceId}` +
            PathConfig.DRIVES + PathConfig.COUNT;
        const params: { [key: string]: string | number } = {};
        if (this._componentType) {
            params['component'] = this._componentType;
        }
        if (this._query) {
            params['query'] = this._query;
        }
        if (this._tagIds && this._tagIds.length) {
            params['tagIds'] = this._tagIds.join(',');
        }
        return new DriveCountRequest(path, params);
    }

    public workspaceId(workspaceId: string): Builder {
        this._workspaceId = workspaceId;
        return this;
    }

    public componentType(componentType: DriveComponentType): Builder {
        this._componentType = componentType;
        return this;
    }

    public query(query: string): Builder {
        this._query = query;
        return this;
    }

    public tagIds(tagIds: string[]): Builder {
        this._tagIds = tagIds;
        return this;
    }
}