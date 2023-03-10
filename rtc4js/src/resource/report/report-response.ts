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
 * Report Resource Response model
 */
export class ReportResponse {

    constructor(
        public id: string = '',
        public formId: string = '',
        public fields: { [key: string]: unknown } = {},
    ) {
    }

    public static of({ id, formId, fields }: ReportResponse): ReportResponse {
        return new ReportResponse(id, formId, fields);
    }

    public getFieldAs<T>(fieldId: string): T | null {
        if (this.fields === null || typeof this.fields !== 'object') {
            return null;
        }
        const value = fieldId in this.fields ? this.fields[fieldId] : null;
        if (value === null) {
            return null;
        }
        return value as T;
    }

    public getFieldAsList(fieldId: string): string[] {
        const data = this.getFieldAs<string[]>(fieldId) || [];
        return Array.isArray(data) ? data : [];
    }

    public getFieldAsString(fieldId: string): string {
        return this.getFieldAs<string>(fieldId) || '';
    }

    public getFieldAsDate(fieldId: string): Date | null {
        const value = this.getFieldAs<Date>(fieldId);
        if (typeof value === 'string') {
            try {
                return new Date(value);
            } catch {
                return null;
            }
        } else if (value instanceof Date) {
            return value;
        }
        return null;
    }

    public setField(fieldId: string, value: unknown): void {
        this.fields[fieldId] = value;
    }
}
