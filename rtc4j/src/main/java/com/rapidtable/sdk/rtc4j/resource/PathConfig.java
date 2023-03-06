/*
 * Copyright Rapid Table, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License. A copy of the License is located at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package com.rapidtable.sdk.rtc4j.resource;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

public class PathConfig {
    public static final String ROOT = "/restapi/v1";
    public static final String PERMISSIONS = "/permissions";
    public static final String WORKSPACE = "/workspace";
    public static final String DRIVES = "/drives";
    public static final String PROJECTS = "/projects";
    public static final String REPORTS = "/reports";
    public static final String OBJECTS = "/objects";
    public static final String METADATA = "/metadata";
    public static final String SEARCH = "/search";
    public static final String COUNT = "/count";

    public static final Pattern OBJECT_PATH_PATTERN = Pattern
        .compile("^reports/(?<wid>[0-9a-z_-]+)/(?<pid>[0-9a-z_-]+)/(?<rid>[0-9a-z_-]+)/objects/(?<oid>[0-9a-z_-]+).*",
            CASE_INSENSITIVE);
}
