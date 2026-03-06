package com.pranav.leave.service.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.pranav.leave.dto.response.AttendanceResponse;
import com.pranav.leave.entity.Attendance;
import com.pranav.leave.entity.User;
import com.pranav.leave.repository.AttendanceRepository;
import com.pranav.leave.repository.UserRepository;
import com.pranav.leave.service.AttendanceService;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository,
                                 UserRepository userRepository) {
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public String checkIn() {

        User user = getCurrentUser();
        LocalDate today = LocalDate.now();

        if (attendanceRepository.findByUserAndDate(user, today).isPresent()) {
            throw new RuntimeException("Already checked in today");
        }

        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setDate(today);
        attendance.setCheckIn(LocalDateTime.now());

        attendanceRepository.save(attendance);

        return "Check-in successful";
    }

    @Override
    public String checkOut() {

        User user = getCurrentUser();
        LocalDate today = LocalDate.now();

        Attendance attendance = attendanceRepository
                .findByUserAndDate(user, today)
                .orElseThrow(() -> new RuntimeException("You must check-in first"));

        if (attendance.getCheckOut() != null) {
            throw new RuntimeException("Already checked out");
        }

        LocalDateTime checkOutTime = LocalDateTime.now();

        attendance.setCheckOut(checkOutTime);

        long minutes = Duration.between(attendance.getCheckIn(), checkOutTime).toMinutes();

        attendance.setWorkingHours(minutes / 60.0);

        attendanceRepository.save(attendance);

        return "Check-out successful";
    }

    @Override
    public List<AttendanceResponse> getMyAttendance() {

        User user = getCurrentUser();

        List<Attendance> records = attendanceRepository.findByUser(user);

        List<AttendanceResponse> responses = new ArrayList<>();

        for (Attendance record : records) {

            AttendanceResponse res = new AttendanceResponse();

            res.setDate(record.getDate());
            res.setCheckIn(record.getCheckIn());
            res.setCheckOut(record.getCheckOut());
            res.setWorkingHours(record.getWorkingHours());

            responses.add(res);
        }

        return responses;
    }
}