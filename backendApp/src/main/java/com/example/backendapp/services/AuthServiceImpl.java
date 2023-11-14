package com.example.backendapp.services;

import com.example.backendapp.dto.AuthRequestDto;
import com.example.backendapp.dto.AuthResponseDto;
import com.example.backendapp.model.User;
import com.example.backendapp.repository.UserRepository;
import com.example.backendapp.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtTokenService;

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public AuthResponseDto checkUserCredentials(AuthRequestDto loginRequestDto) {

        if (loginRequestDto.getEmail() == null || loginRequestDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email field is required");
        }
        if (loginRequestDto.getPassword() == null || loginRequestDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password field is required");
        }


        User userEntity = userRepository.findByEmail(loginRequestDto.getEmail());

        if (userEntity != null && passwordEncoder.matches(loginRequestDto.getPassword(),userEntity.getPassword())) {
            AuthResponseDto responseDto = new AuthResponseDto();
            responseDto.setEmail(userEntity.getEmail());
            responseDto.setLastName(userEntity.getLastName());
            return responseDto;
        }
        return null;
    }


    @Override
    public String createToken(AuthResponseDto loginResponseDto) {

        String jwt = jwtTokenService.generateToken(loginResponseDto.getEmail(), loginResponseDto.getLastName());
        return jwt;
    }

    @Override
    public HttpHeaders createHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return headers;
    }



}
