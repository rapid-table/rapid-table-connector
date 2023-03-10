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

export class DriveResponse {
    /**
     * Drive Resource Response model
     * @param id Drive asset ID
     * @param path Drive asset path
     * @param size Drive asset size
     * @param tagIds Tag ID assigned to the asset
     * @param date Drive asset update date
     * @param contentType Drive asset content type
     * @param name Drive asset name
     */
    constructor(public id: string,
        public path: string = '',
        public size: number = NaN,
        public tagIds: string[] = [],
        public date: Date | null = null,
        public contentType: string = '',
        public name: string = '') { }

    public static of({ id, path, size, tagIds, date, contentType, name }: DriveResponse): DriveResponse {
        return new DriveResponse(id, path, size, tagIds, date, contentType, name);
    }
}
