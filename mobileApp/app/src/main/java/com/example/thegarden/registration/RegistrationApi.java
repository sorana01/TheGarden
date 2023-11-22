package com.example.thegarden.registration;

import com.example.thegarden.dto.RegistrationRequestDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegistrationApi {
    @POST("/api/v1/auth/registration")
    Call<RegistrationRequestDto> saveUser(@Body RegistrationRequestDto registrationRequestDto);
}
