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
import { ReportRequestModel } from './report-request-model';

export class ReportCreateRequest implements IRequest {
    constructor(public path: string, public body: string | null) { }

    public getPath(): string {
        return this.path;
    }

    public getQuery(): { [key: string]: string | number } {
        return {};
    }

    public getBody(): string | null {
        return this.body;
    }

    public static builder(): Builder {
        return new Builder();
    }

}

class Builder {
    private _workspaceId: string | null = null;
    private _projectId: string | null = null;
    private _reports: ReportRequestModel[] = [];

    public build(): ReportCreateRequest {
        const path = PathConfig.ROOT + PathConfig.WORKSPACE + `/${this._workspaceId}` +
            PathConfig.PROJECTS + `/${this._projectId}` + PathConfig.REPORTS;

        if (!this._reports || !this._reports.length) {
            throw new Error('IllegalArgumentException');
        }
        if (this._reports.length > 100) {
            throw new Error('IllegalArgumentException');
        }

        return new ReportCreateRequest(path, JSON.stringify(this._reports));
    }

    public workspaceId(workspaceId: string): Builder {
        this._workspaceId = workspaceId;
        return this;
    }

    public projectId(projectId: string): Builder {
        this._projectId = projectId;
        return this;
    }

    public append(fields: { [key: string]: unknown }): Builder {
        this._reports.push(new ReportRequestModel(null, null, fields));
        return this;
    }

}