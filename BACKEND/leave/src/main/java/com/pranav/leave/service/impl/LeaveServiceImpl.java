package com.pranav.leave.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.pranav.leave.dto.request.LeaveRequest;
import com.pranav.leave.dto.request.ReviewLeaveRequest;
import com.pranav.leave.dto.response.LeaveResponse;
import com.pranav.leave.entity.Department;
import com.pranav.leave.entity.LeaveApplication;
import com.pranav.leave.entity.LeaveStatus;
import com.pranav.leave.entity.User;
import com.pranav.leave.repository.LeaveApplicationRepository;
import com.pranav.leave.repository.UserRepository;
import com.pranav.leave.service.LeaveService;

@Service
public class LeaveServiceImpl implements LeaveService {

    private final LeaveApplicationRepository leaveRepository;
    private final UserRepository userRepository;

    public LeaveServiceImpl(LeaveApplicationRepository leaveRepository,
                            UserRepository userRepository) {
        this.leaveRepository = leaveRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String applyLeave(LeaveRequest request) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LeaveApplication leave = new LeaveApplication();
        leave.setUser(user);
        leave.setLeaveType(request.getLeaveType());
        leave.setStartDate(request.getStartDate());
        leave.setEndDate(request.getEndDate());
        leave.setReason(request.getReason());
        leave.setStatus(LeaveStatus.PENDING);
        leave.setAppliedAt(LocalDateTime.now());

        leaveRepository.save(leave);

        return "Leave applied successfully and is pending approval.";
    }
    
    @Override
    public List<LeaveResponse> getPendingLeavesForManager() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User manager = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        Department department = manager.getDepartment();

        List<LeaveApplication> leaves =
                leaveRepository.findByStatusAndUser_Department(
                        LeaveStatus.PENDING,
                        department);

        List<LeaveResponse> responseList = new ArrayList<>();

        for (LeaveApplication leave : leaves) {

            LeaveResponse response = new LeaveResponse();

            response.setId(leave.getId());
            response.setLeaveType(leave.getLeaveType());
            response.setStartDate(leave.getStartDate());
            response.setEndDate(leave.getEndDate());
            response.setReason(leave.getReason());
            response.setStatus(leave.getStatus());
            response.setAppliedAt(leave.getAppliedAt());
            response.setManagerComment(leave.getManagerComment());
            response.setReviewedAt(leave.getReviewedAt());

            responseList.add(response);
        }

        return responseList;
    }
    
    @Override
    public String reviewLeave(Long leaveId, ReviewLeaveRequest request) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User manager = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        LeaveApplication leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        // 1️⃣ Only PENDING can be reviewed
        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new RuntimeException("Leave already reviewed");
        }

        // 2️⃣ Manager must belong to same department
        if (!leave.getUser().getDepartment().getId()
                .equals(manager.getDepartment().getId())) {
            throw new RuntimeException("You cannot review leave from another department");
        }

        // 3️⃣ Update leave
        leave.setStatus(request.getStatus());
        leave.setManagerComment(request.getComment());
        leave.setReviewedAt(java.time.LocalDateTime.now());

        leaveRepository.save(leave);

        return "Leave " + request.getStatus().name() + " successfully.";
    }
    
    @Override
    public List<LeaveResponse> getMyLeaves() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<LeaveApplication> leaves = leaveRepository.findByUser(user);

        List<LeaveResponse> responseList = new ArrayList<>();

        for (LeaveApplication leave : leaves) {

            LeaveResponse response = new LeaveResponse();

            response.setId(leave.getId());
            response.setLeaveType(leave.getLeaveType());
            response.setStartDate(leave.getStartDate());
            response.setEndDate(leave.getEndDate());
            response.setReason(leave.getReason());
            response.setStatus(leave.getStatus());
            response.setManagerComment(leave.getManagerComment());
            response.setAppliedAt(leave.getAppliedAt());
            response.setReviewedAt(leave.getReviewedAt());

            responseList.add(response);
        }

        return responseList;
    }
}