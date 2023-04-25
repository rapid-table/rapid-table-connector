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

export class PathConfig {
    public static ROOT: string = '/restapi/v1';
    public static PERMISSIONS: string = '/permissions';
    public static WORKSPACE: string = '/workspace';
    public static DRIVES: string = '/drives';
    public static PROJECTS: string = '/projects';
    public static REPORTS: string = '/reports';
    public static SCHEMA: string = '/schema';
    public static OBJECTS: string = '/objects';
    public static METADATA: string = '/metadata';
    public static SEARCH: string = '/search';
    public static COUNT: string = '/count';
    public static AGGREGATE: string = '/aggs';

    public static REPORT_OBJECT_PATH_PATTERN = new RegExp(/^reports\/(?<wid>[a-z0-9_-]+?)\/(?<pid>[a-z0-9_-]+?)\/(?<rid>[a-z0-9_-]+?)\/objects\/(?<oid>[a-z0-9_-]+)/i);
    public static DRIVE_OBJECT_PATH_PATTERN = new RegExp(/^drives\/(?<wid>[a-z0-9_-]+?)\/objects\/(?<oid>[a-z0-9_-]+)/i);
}
