package com.pranav.leave.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import com.pranav.leave.dto.request.LeaveRequest;
import com.pranav.leave.dto.request.ReviewLeaveRequest;
import com.pranav.leave.dto.response.LeaveResponse;
import com.pranav.leave.entity.LeaveApplication;
import com.pranav.leave.service.LeaveService;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping("/apply")
    public String applyLeave(@RequestBody LeaveRequest request) {
        return leaveService.applyLeave(request);
    }
    
    @GetMapping("/pending")
    public List<LeaveResponse> getPendingLeaves() {
        return leaveService.getPendingLeavesForManager();
    }
    
    @PutMapping("/{id}/review")
    public String reviewLeave(
            @PathVariable Long id,
            @RequestBody ReviewLeaveRequest request) {

        return leaveService.reviewLeave(id, request);
    }
    
    @GetMapping("/my-leaves")
    public List<LeaveResponse> getMyLeaves() {
        return leaveService.getMyLeaves();
    }
}