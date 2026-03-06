package com.pranav.leave.service;

import java.util.List;

import com.pranav.leave.dto.response.AttendanceResponse;

public interface AttendanceService {

    String checkIn();

    String checkOut();

    List<AttendanceResponse> getMyAttendance();
}