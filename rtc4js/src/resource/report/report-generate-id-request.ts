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

import { IGenerateIdRequest } from '../generate-id-request.interface';
import { PathConfig } from '../path-config';

export class ReportGenerateIdRequest implements IGenerateIdRequest {

    constructor(public path: string) {
        this.path = path;
    }

    public getPath(): string {
        return this.path;
    }

    public getQuery(): string | null {
        return null;
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
    private _projectId: string | null = null;

    public build(): ReportGenerateIdRequest {
        const path = PathConfig.ROOT + PathConfig.WORKSPACE + `/${this._workspaceId}` +
            PathConfig.PROJECTS + `/${this._projectId}` +
            PathConfig.REPORTS + '/generateId';

        return new ReportGenerateIdRequest(path);
    }

    public workspaceId(workspaceId: string): Builder {
        this._workspaceId = workspaceId;
        return this;
    }

    public projectId(projectId: string): Builder {
        this._projectId = projectId;
        return this;
    }

}