package com.rapidtable.sdk.rtc4j.resource.report;

import java.time.Instant;
import java.util.List;

/**
 * Report approval model
 */
public class ReportApproval {
    /**
     * Status
     */
    private ReportApprovalStatus status;
    /**
     * Approval phase
     * If there are multiple approval steps, this stores the current step number
     */
    private Integer phase;
    /**
     * Editor user IDs
     */
    private List<String> editorIds;
    /**
     * User IDs of the current approvers
     */
    private List<String> holderIds;
    /**
     * User group IDs of the current approvers
     */
    private List<String> holderGroupIds;
    /**
     * Approval deadline
     */
    private Instant deadline;

    public ReportApproval() {
    }

    public ReportApproval(ReportApprovalStatus status, Integer phase, List<String> editorIds, List<String> holderIds, List<String> holderGroupIds, Instant deadline) {
        this.status = status;
        this.phase = phase;
        this.editorIds = editorIds;
        this.holderIds = holderIds;
        this.holderGroupIds = holderGroupIds;
        this.deadline = deadline;
    }

    public ReportApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(ReportApprovalStatus status) {
        this.status = status;
    }

    public Integer getPhase() {
        return phase;
    }

    public void setPhase(Integer phase) {
        this.phase = phase;
    }

    public List<String> getEditorIds() {
        return editorIds;
    }

    public void setEditorIds(List<String> editorIds) {
        this.editorIds = editorIds;
    }

    public List<String> getHolderIds() {
        return holderIds;
    }

    public void setHolderIds(List<String> holderIds) {
        this.holderIds = holderIds;
    }

    public List<String> getHolderGroupIds() {
        return holderGroupIds;
    }

    public void setHolderGroupIds(List<String> holderGroupIds) {
        this.holderGroupIds = holderGroupIds;
    }

    public Instant getDeadline() {
        return deadline;
    }

    public void setDeadline(Instant deadline) {
        this.deadline = deadline;
    }
}
