package com.pranav.leave.dto.response;

public class DashboardResponse {

    private long totalLeaves;
    private long approvedLeaves;
    private long rejectedLeaves;
    private long pendingLeaves;

    public long getTotalLeaves() { return totalLeaves; }
    public void setTotalLeaves(long totalLeaves) { this.totalLeaves = totalLeaves; }

    public long getApprovedLeaves() { return approvedLeaves; }
    public void setApprovedLeaves(long approvedLeaves) { this.approvedLeaves = approvedLeaves; }

    public long getRejectedLeaves() { return rejectedLeaves; }
    public void setRejectedLeaves(long rejectedLeaves) { this.rejectedLeaves = rejectedLeaves; }

    public long getPendingLeaves() { return pendingLeaves; }
    public void setPendingLeaves(long pendingLeaves) { this.pendingLeaves = pendingLeaves; }
}