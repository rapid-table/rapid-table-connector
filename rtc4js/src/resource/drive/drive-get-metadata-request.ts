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

export class DriveGetMetadataRequest implements IRequest {
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
    private _objectId: string | null = null;

    public build(): DriveGetMetadataRequest {
        const path = PathConfig.ROOT + PathConfig.WORKSPACE + `/${this._workspaceId}` +
            PathConfig.DRIVES + PathConfig.METADATA + `/${this._objectId}`;
        return new DriveGetMetadataRequest(path, {});
    }

    public workspaceId(workspaceId: string): Builder {
        this._workspaceId = workspaceId;
        return this;
    }

    public objectId(objectId: string): Builder {
        this._objectId = objectId;
        return this;
    }
}