package com.pranav.leave.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.pranav.leave.dto.response.AttendanceResponse;
import com.pranav.leave.service.AttendanceService;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/check-in")
    public String checkIn() {
        return attendanceService.checkIn();
    }

    @PostMapping("/check-out")
    public String checkOut() {
        return attendanceService.checkOut();
    }

    @GetMapping("/my-records")
    public List<AttendanceResponse> getMyAttendance() {
        return attendanceService.getMyAttendance();
    }
}