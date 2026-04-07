package com.rapidtable.sdk.rtc4j.resource.report;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Approval status
 */
public enum ReportApprovalStatus {
    // Editing
    editing(0),
    // Approved
    accepted(1),
    // Pending approval
    waiting(2),
    // Sent back / Returned
    revert(3),
    // On hold
    defer(4),
    // Rejected
    reject(5);

    private final int code;

    ReportApprovalStatus(int value) {
        this.code = value;
    }

    @JsonValue
    public int getCode() {
        return this.code;
    }

}
