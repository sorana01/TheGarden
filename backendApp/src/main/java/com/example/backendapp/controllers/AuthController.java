package com.example.backendapp.controllers;


import com.example.backendapp.dto.AuthRequestDto;
import com.example.backendapp.dto.AuthResponseDto;
import com.example.backendapp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
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
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("email", loginResponseDto.getEmail());
                responseBody.put("lastName", loginResponseDto.getLastName());
                responseBody.put("message", "Login successful");
                return ResponseEntity.ok().headers(headers).body(responseBody);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Failed to create authentication token"));
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Invalid email or password"));
    }
}
