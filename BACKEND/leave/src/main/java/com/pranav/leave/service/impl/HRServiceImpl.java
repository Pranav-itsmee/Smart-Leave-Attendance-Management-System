package com.pranav.leave.service.impl;

import org.springframework.stereotype.Service;

import com.pranav.leave.dto.response.DashboardResponse;
import com.pranav.leave.entity.LeaveStatus;
import com.pranav.leave.repository.LeaveApplicationRepository;
import com.pranav.leave.service.HRService;

@Service
public class HRServiceImpl implements HRService {

    private final LeaveApplicationRepository leaveRepository;

    public HRServiceImpl(LeaveApplicationRepository leaveRepository) {
        this.leaveRepository = leaveRepository;
    }

    @Override
    public DashboardResponse getLeaveSummary() {

        DashboardResponse response = new DashboardResponse();

        response.setTotalLeaves(leaveRepository.count());
        response.setApprovedLeaves(leaveRepository.countByStatus(LeaveStatus.APPROVED));
        response.setRejectedLeaves(leaveRepository.countByStatus(LeaveStatus.REJECTED));
        response.setPendingLeaves(leaveRepository.countByStatus(LeaveStatus.PENDING));

        return response;
    }
}