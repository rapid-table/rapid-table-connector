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

import FormData from 'form-data';
import { PathConfig } from '../path-config';
import { IPutObjectRequest } from '../put-object-request.interface';

export class DrivePutObjectRequest implements IPutObjectRequest {
  constructor(public path: string, public formData: FormData) {}

  public getPath(): string {
    return this.path;
  }

  public getFormData(): any {
    return this.formData;
  }

  public static builder(): Builder {
    return new Builder();
  }
}

class Builder {
  private _workspaceId: string | null = null;
  private _formData: FormData | null = null;

  public build(): DrivePutObjectRequest {
    const path =
      PathConfig.ROOT +
      PathConfig.WORKSPACE +
      `/${this._workspaceId}` +
      PathConfig.DRIVES +
      PathConfig.OBJECTS;

    if (!this._formData) {
      throw new Error('IllegalArgumentException');
    }

    return new DrivePutObjectRequest(path, this._formData);
  }

  public workspaceId(workspaceId: string): Builder {
    this._workspaceId = workspaceId;
    return this;
  }

  public append(buffer: any, fileName: string): Builder {
    if (!this._formData) {
      this._formData = new FormData();
    }
    if (fileName.includes('/')) {
      this._formData.append('file', buffer, { filepath: fileName });
    } else {
      this._formData.append('file', buffer, fileName);
    }
    return this;
  }
}
