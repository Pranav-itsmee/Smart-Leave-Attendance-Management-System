package com.pranav.leave.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttendanceResponse {

    private LocalDate date;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Double workingHours;

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalDateTime getCheckIn() { return checkIn; }
    public void setCheckIn(LocalDateTime checkIn) { this.checkIn = checkIn; }

    public LocalDateTime getCheckOut() { return checkOut; }
    public void setCheckOut(LocalDateTime checkOut) { this.checkOut = checkOut; }

    public Double getWorkingHours() { return workingHours; }
    public void setWorkingHours(Double workingHours) { this.workingHours = workingHours; }
}