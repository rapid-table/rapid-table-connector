/**
 * Report approval model
 */
export type ReportApproval = {
  /**
   * Status
   */
  status: ReportApprovalStatus;
  /**
   * Approval phase
   * If there are multiple approval steps, this stores the current step number
   */
  phase: number;
  /**
   * Editor user IDs
   */
  editorIds: string[];
  /**
   * User IDs of the current approvers
   */
  holderIds: string[];
  /**
   * User group IDs of the current approvers
   */
  holderGroupIds: string[];
  /**
   * Approval deadline
   */
  deadline: Date | null;
};

/**
 * Approval status
 */
export enum ReportApprovalStatus {
  // Editing
  editing = 0,
  // Approved
  accepted = 1,
  // Pending approval
  waiting = 2,
  // Sent back / Returned
  revert = 3,
  // On hold
  defer = 4,
  // Rejected
  reject = 5,
}
