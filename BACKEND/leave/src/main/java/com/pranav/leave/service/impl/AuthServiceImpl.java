package com.pranav.leave.service.impl;

import org.springframework.security.authentication.*;
import com.pranav.leave.repository.DepartmentRepository;
import com.pranav.leave.entity.Department;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.pranav.leave.dto.request.LoginRequest;
import com.pranav.leave.dto.request.RegisterRequest;
import com.pranav.leave.dto.response.AuthResponse;
import com.pranav.leave.entity.User;
import com.pranav.leave.repository.UserRepository;
import com.pranav.leave.security.JwtTokenProvider;
import com.pranav.leave.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final DepartmentRepository departmentRepository;

    public AuthServiceImpl(UserRepository userRepository,
            				BCryptPasswordEncoder passwordEncoder,
            				JwtTokenProvider jwtTokenProvider,
            				AuthenticationManager authenticationManager,
            				DepartmentRepository departmentRepository) {
    	this.userRepository = userRepository;
    	this.passwordEncoder = passwordEncoder;
    	this.jwtTokenProvider = jwtTokenProvider;
    	this.authenticationManager = authenticationManager;
    	this.departmentRepository = departmentRepository;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return new AuthResponse(null, "Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        
        Department department = departmentRepository
                .findByName(request.getDepartmentName())
                .orElseGet(() -> {
                    Department newDept = new Department();
                    newDept.setName(request.getDepartmentName());
                    newDept.setDescription(request.getDepartmentName() + " Department");
                    return departmentRepository.save(newDept);
                });

        user.setDepartment(department);

        userRepository.save(user);

        String token = jwtTokenProvider.generateToken(user.getEmail());

        return new AuthResponse(token, "User registered successfully");
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token = jwtTokenProvider.generateToken(request.getEmail());

        return new AuthResponse(token, "Login successful");
    }
}