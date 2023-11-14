package com.example.backendapp.services;

import com.example.backendapp.dto.RegistrationRequestDto;
import com.example.backendapp.exceptions.InvalidCredentialException;
import com.example.backendapp.model.User;

public interface RegistrationService {
    User register(RegistrationRequestDto request) throws InvalidCredentialException;
}
