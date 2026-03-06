package com.pranav.leave.service;

import java.util.List;
import com.pranav.leave.dto.request.LeaveRequest;
import com.pranav.leave.dto.request.ReviewLeaveRequest;
import com.pranav.leave.dto.response.LeaveResponse;
import com.pranav.leave.entity.LeaveApplication;

public interface LeaveService {

    String applyLeave(LeaveRequest request);
    List<LeaveResponse> getPendingLeavesForManager();
    String reviewLeave(Long leaveId, ReviewLeaveRequest request);
    List<LeaveResponse> getMyLeaves();
}