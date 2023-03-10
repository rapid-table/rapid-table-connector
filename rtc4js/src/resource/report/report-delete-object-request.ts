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

import { IDeleteRequest } from '../delete-request.interface';
import { PathConfig } from '../path-config';

export class ReportDeleteObjectRequest implements IDeleteRequest {

    constructor(public path: string) {
        this.path = path;
    }

    public getPath(): string {
        return this.path;
    }

    public getQuery(): { [key: string]: string | number } {
        return {};
    }

    public static builder(): Builder {
        return new Builder();
    }

}

class Builder {
    private _workspaceId: string | null = null;
    private _projectId: string | null = null;
    private _reportId: string | null = null;
    private _objectId: string | null = null;
    private _target: string | null = null;

    public build(): ReportDeleteObjectRequest {
        if (this._target) {
            const matcher = PathConfig.REPORT_OBJECT_PATH_PATTERN.exec(this._target);
            if (!matcher) {
                throw new Error('IllegalArgumentException');
            }

            const workspaceId = matcher.groups?.wid;
            const projectId = matcher.groups?.pid;
            const reportId = matcher.groups?.rid;
            const objectId = matcher.groups?.oid;
            const path = PathConfig.ROOT + PathConfig.WORKSPACE + `/${workspaceId}` +
                PathConfig.PROJECTS + `/${projectId}` +
                PathConfig.REPORTS + `/${reportId}` +
                PathConfig.OBJECTS + `/${objectId}`;

            return new ReportDeleteObjectRequest(path);
        } else {
            const path = PathConfig.ROOT + PathConfig.WORKSPACE + `/${this._workspaceId}` +
                PathConfig.PROJECTS + `/${this._projectId}` +
                PathConfig.REPORTS + `/${this._reportId}` +
                PathConfig.OBJECTS + `/${this._objectId}`;

            return new ReportDeleteObjectRequest(path);
        }
    }

    public workspaceId(workspaceId: string): Builder {
        this._workspaceId = workspaceId;
        return this;
    }

    public projectId(projectId: string): Builder {
        this._projectId = projectId;
        return this;
    }

    public reportId(reportId: string): Builder {
        this._reportId = reportId;
        return this;
    }

    public objectId(objectId: string): Builder {
        this._objectId = objectId;
        return this;
    }

    public target(target: string): Builder {
        this._target = target;
        return this;
    }
}