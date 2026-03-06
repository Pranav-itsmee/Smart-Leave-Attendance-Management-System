package com.pranav.leave.dto.request;

import com.pranav.leave.entity.LeaveStatus;

public class ReviewLeaveRequest {

    private LeaveStatus status;  // APPROVED or REJECTED
    private String comment;

    public LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveStatus status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}