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

export class ReportGetObjectRequest implements IRequest {

    constructor(public path: string, public query: { [key: string]: string | number }) {
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
    private _path: string | null = null;

    public build(): ReportGetObjectRequest {
        if (!this._path) {
            throw new Error('IllegalArgumentException');
        }
        return new ReportGetObjectRequest(this._path, {});
    }

    public target(value: unknown): Builder {
        if (value !== null && typeof value === 'string') {
            const matcher = PathConfig.REPORT_OBJECT_PATH_PATTERN.exec(value);
            if (matcher && matcher.groups) {
                const workspaceId = matcher.groups.wid;
                const projectId = matcher.groups.pid;
                const reportId = matcher.groups.rid;
                const objectId = matcher.groups.oid;
                this._path = PathConfig.ROOT + PathConfig.WORKSPACE + `/${workspaceId}` +
                    PathConfig.PROJECTS + `/${projectId}` +
                    PathConfig.REPORTS + `/${reportId}` +
                    PathConfig.OBJECTS + `/${objectId}`;
            }
        }
        return this;
    }
}