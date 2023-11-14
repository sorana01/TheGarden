package com.example.backendapp.controllers;


import com.example.backendapp.dto.AuthRequestDto;
import com.example.backendapp.dto.AuthResponseDto;
import com.example.backendapp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    private ResponseEntity<?> loginUser(@RequestBody AuthRequestDto loginRequestDto) {
        AuthResponseDto loginResponseDto = authService.checkUserCredentials(loginRequestDto);

        if (loginResponseDto != null) {
            String token = authService.createToken(loginResponseDto);

            if (token != null) {
                HttpHeaders headers = authService.createHeader(token);
                //Map<String, String> responseBody = authService.createSuccessLoginResponse(token);
                return ResponseEntity.ok().headers(headers).body("Login successful");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to create authentication token");
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }
}
