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

package com.rapidtable.sdk.rtc4j.resource.report;

import com.rapidtable.sdk.rtc4j.exceptions.NotImplementedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class ReportSearchBuilderBase<T> {
    protected String workspaceId = null;
    protected String projectId = null;
    protected Integer page = null;
    protected Integer size = null;
    protected String asc = null;
    protected String desc = null;
    protected String query = null;
    protected String emp = null;
    protected String noemp = null;
    protected List<String> includes = null;
    protected List<String> in = null;
    protected String eq = null;
    protected String neq = null;
    protected String lt = null;
    protected String lte = null;
    protected String gt = null;
    protected String gte = null;
    protected String term = null;
    protected List<String> ids = new ArrayList<>();

    protected ReportSearchBuilderBase() {
    }

    public T build() throws Exception {
        throw new NotImplementedException();
    }

    public ReportSearchBuilderBase<T> workspaceId(final String workspaceId) {
        this.workspaceId = workspaceId;
        return this;
    }

    public ReportSearchBuilderBase<T> projectId(final String projectId) {
        this.projectId = projectId;
        return this;
    }

    public ReportSearchBuilderBase<T> page(final Integer page) {
        this.page = page;
        return this;
    }

    public ReportSearchBuilderBase<T> size(final Integer size) {
        this.size = size;
        return this;
    }

    /**
     * Ascending sort field.
     * - Any
     * asc=fieldId
     * - Creation date
     * asc=cdt
     * - Update Date
     * asc=udt
     */
    public ReportSearchBuilderBase<T> asc(final String fieldId) {
        this.asc = fieldId;
        return this;
    }

    /**
     * Descending sort field.
     * - Any
     * desc=fieldId
     * - Creation date
     * desc=cdt
     * - Update Date
     * desc=udt
     */
    public ReportSearchBuilderBase<T> desc(final String fieldId) {
        this.desc = fieldId;
        return this;
    }

    /**
     * Search string.
     * query=searchText
     */
    public ReportSearchBuilderBase<T> query(final String query) {
        this.query = query;
        return this;
    }

    /**
     * Word search (blank fields only).
     * emp=fieldId
     */
    public ReportSearchBuilderBase<T> emp(final String fieldId) {
        this.emp = fieldId;
        return this;
    }

    /**
     * Word search (non-blank fields only).
     * noemp=fieldId
     */
    public ReportSearchBuilderBase<T> noemp(final String fieldId) {
        this.noemp = fieldId;
        return this;
    }

    /**
     * Exact match for specific field.
     * includes=fieldId1:searchText1;fieldId2:searchText2
     * - For example, the arguments are as follows
     * "fieldId1:searchText1", "fieldId2:searchText2",...
     */
    public ReportSearchBuilderBase<T> includes(final String... includes) {
        this.includes = Arrays.stream(includes).toList();
        return this;
    }

    /**
     * Partial match for specific field.
     * in=fieldId1:searchText1;fieldId2:searchText2
     * - For example, the arguments are as follows
     * "fieldId1:searchText1", "fieldId2:searchText2",...
     */
    public ReportSearchBuilderBase<T> in(final String... in) {
        this.in = Arrays.stream(in).toList();
        return this;
    }

    /**
     * Numeric value (equals).
     * eq=fieldId:123
     */
    public ReportSearchBuilderBase<T> eq(final String fieldId, final String value) {
        this.eq = String.format("%s:%s", fieldId, value);
        return this;
    }

    /**
     * Numeric value (not equal).
     * eq=fieldId:123
     */
    public ReportSearchBuilderBase<T> neq(final String fieldId, final String value) {
        this.neq = String.format("%s:%s", fieldId, value);
        return this;
    }

    /**
     * Numeric value (less than).
     * eq=fieldId:123
     */
    public ReportSearchBuilderBase<T> lt(final String fieldId, final String value) {
        this.lt = String.format("%s:%s", fieldId, value);
        return this;
    }

    /**
     * Numeric value (less than or equal to).
     * eq=fieldId:123
     */
    public ReportSearchBuilderBase<T> lte(final String fieldId, final String value) {
        this.lte = String.format("%s:%s", fieldId, value);
        return this;
    }

    /**
     * Numeric value (greater than).
     * eq=fieldId:123
     */
    public ReportSearchBuilderBase<T> gt(final String fieldId, final String value) {
        this.gt = String.format("%s:%s", fieldId, value);
        return this;
    }

    /**
     * Numeric value (grater than or equal to).
     * eq=fieldId:123
     */
    public ReportSearchBuilderBase<T> gte(final String fieldId, final String value) {
        this.gte = String.format("%s:%s", fieldId, value);
        return this;
    }

    /**
     * Date type period search (range).
     * yyyy-MM-dd
     */
    public ReportSearchBuilderBase<T> term(final String fieldId, final String from, final String to) {
        this.term = String.format("%s:%s,%s", fieldId, from, to);
        return this;
    }

    /**
     * Date type period search (from).
     * yyyy-MM-dd
     */
    public ReportSearchBuilderBase<T> termFrom(final String fieldId, final String from) {
        this.term = String.format("%s:%s,", fieldId, from);
        return this;
    }

    /**
     * Date type period search (to).
     * yyyy-MM-dd
     */
    public ReportSearchBuilderBase<T> termTo(final String fieldId, final String to) {
        this.term = String.format("%s:,%s", fieldId, to);
        return this;
    }

    /**
     * Write the report IDs separated by commas.
     * ids=report-id1,report-id2,report-id3
     */
    public ReportSearchBuilderBase<T> ids(final String... ids) {
        this.ids.addAll(Arrays.asList(ids));
        return this;
    }

    public List<String> getParams() {
        final var params = new ArrayList<String>();
        if (Objects.nonNull(page)) {
            params.add("page=" + page);
        }
        if (Objects.nonNull(size)) {
            params.add("size=" + size);
        }

        if (Objects.nonNull(asc)) {
            params.add("asc=" + asc);
        }
        if (Objects.nonNull(desc)) {
            params.add("desc=" + desc);
        }
        if (Objects.nonNull(query)) {
            params.add("query=" + query);
        }
        if (Objects.nonNull(emp)) {
            params.add("emp=" + emp);
        }
        if (Objects.nonNull(noemp)) {
            params.add("noemp=" + noemp);
        }
        if (Objects.nonNull(includes) && !includes.isEmpty()) {
            params.add("includes=" + String.join(";", includes));
        }
        if (Objects.nonNull(in) && !in.isEmpty()) {
            params.add("in=" + String.join(";", in));
        }
        if (Objects.nonNull(eq)) {
            params.add("eq=" + eq);
        }
        if (Objects.nonNull(neq)) {
            params.add("neq=" + neq);
        }
        if (Objects.nonNull(lt)) {
            params.add("lt=" + lt);
        }
        if (Objects.nonNull(lte)) {
            params.add("lte=" + lte);
        }
        if (Objects.nonNull(gt)) {
            params.add("gt=" + gt);
        }
        if (Objects.nonNull(gte)) {
            params.add("gte=" + gte);
        }
        if (Objects.nonNull(term)) {
            params.add("term=" + term);
        }
        if (Objects.nonNull(ids) && !ids.isEmpty()) {
            params.add("ids=" + String.join(",", ids));
        }
        return params;
    }
}
