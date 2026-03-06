package com.pranav.leave.controller;

import org.springframework.web.bind.annotation.*;

import com.pranav.leave.dto.response.DashboardResponse;
import com.pranav.leave.service.HRService;

@RestController
@RequestMapping("/api/hr")
public class HRController {

    private final HRService hrService;

    public HRController(HRService hrService) {
        this.hrService = hrService;
    }

    @GetMapping("/dashboard")
    public DashboardResponse getDashboard() {
        return hrService.getLeaveSummary();
    }
}