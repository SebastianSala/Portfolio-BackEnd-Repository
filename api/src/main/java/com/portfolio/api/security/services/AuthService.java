package com.portfolio.api.security.services;

import com.portfolio.api.dto.request.LoginRequest;
import com.portfolio.api.dto.request.SignupRequest;
import com.portfolio.api.dto.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  public AuthResponse login(LoginRequest loginRequest) {
    return null;
  }

  public AuthResponse register(SignupRequest SignupRequest) {
    return null;
  }

}
