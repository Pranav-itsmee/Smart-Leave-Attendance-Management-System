package com.pranav.leave.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pranav.leave.entity.Department;

@Repository
public interface DepartmentRepository 
        extends JpaRepository<Department, Long> {

    Optional<Department> findByName(String name);
}