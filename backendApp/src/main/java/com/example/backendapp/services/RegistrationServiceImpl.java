package com.example.backendapp.services;


import com.example.backendapp.dto.RegistrationRequestDto;
import com.example.backendapp.exceptions.InvalidCredentialException;
import com.example.backendapp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final UserService userService;

    @Override
    public User register(RegistrationRequestDto request) throws InvalidCredentialException {
        validateRegistrationRequest(request);
        return userService.signUpUser(
                User.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .email(request.getEmail())
                        .password(request.getPassword())
                        .build()
        );
    }

    private void validateRegistrationRequest(RegistrationRequestDto request) throws InvalidCredentialException {
        if (request.getFirstName() == null || request.getFirstName().isEmpty()) {
            throw new InvalidCredentialException("First name cannot be empty");
        }
        if (request.getLastName() == null || request.getLastName().isEmpty()) {
            throw new InvalidCredentialException("Last name cannot be empty");
        }
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new InvalidCredentialException("Email cannot be empty");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new InvalidCredentialException("Password cannot be empty");
        }
    }
}
