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

/**
 * Project Resource Response model
 */
export class ProjectResponse {
  constructor(
    public id: string = '',
    public name: string = '',
    public description: string = '',
    public theme: string = '',
    public coverPath: string = '',
    public favorite: boolean = false
  ) {}

  public static of({
    id,
    name,
    description,
    theme,
    coverPath,
    favorite,
  }: ProjectResponse): ProjectResponse {
    return new ProjectResponse(
      id,
      name,
      description,
      theme,
      coverPath,
      favorite
    );
  }
}
