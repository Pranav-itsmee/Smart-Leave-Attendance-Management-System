package com.pranav.leave.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pranav.leave.entity.Department;
import com.pranav.leave.entity.LeaveApplication;
import com.pranav.leave.entity.LeaveStatus;
import com.pranav.leave.entity.User;

public interface LeaveApplicationRepository 
        extends JpaRepository<LeaveApplication, Long> {
	
	List<LeaveApplication> findByStatusAndUser_Department(LeaveStatus status, Department department);
	List<LeaveApplication> findByUser(User user);
    List<LeaveApplication> findByUser_Department_IdAndStatus(Long departmentId, LeaveStatus status);
    long countByStatus(LeaveStatus status);
    long countByAppliedAtBetween(LocalDateTime start, LocalDateTime end);

}