package com.pranav.leave.service;

import com.pranav.leave.dto.request.LoginRequest;
import com.pranav.leave.dto.request.RegisterRequest;
import com.pranav.leave.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}