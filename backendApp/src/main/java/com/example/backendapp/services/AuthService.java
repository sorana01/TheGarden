package com.example.backendapp.services;

import com.example.backendapp.dto.AuthRequestDto;
import com.example.backendapp.dto.AuthResponseDto;
import com.example.backendapp.model.User;
import org.springframework.http.HttpHeaders;

import java.util.Map;

public interface AuthService {
    User getUserByEmail(String email);

    AuthResponseDto checkUserCredentials(AuthRequestDto loginRequestDto);

    String createToken(AuthResponseDto loginResponseDto);

    HttpHeaders createHeader(String token);

}
