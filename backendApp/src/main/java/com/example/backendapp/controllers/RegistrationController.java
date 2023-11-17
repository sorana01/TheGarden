package com.example.backendapp.controllers;

import com.example.backendapp.dto.RegistrationRequestDto;
import com.example.backendapp.exceptions.InvalidCredentialException;
import com.example.backendapp.model.User;
import com.example.backendapp.services.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/auth/registration")
    public ResponseEntity<String> register(@RequestBody RegistrationRequestDto request) {
        try {
            registrationService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created");
        } catch (InvalidCredentialException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
