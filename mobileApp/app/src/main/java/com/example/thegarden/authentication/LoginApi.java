package com.example.thegarden.authentication;

import com.example.thegarden.dto.AuthRequestDto;
import com.example.thegarden.dto.AuthResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApi {
    @POST("/api/v1/auth/login")
    Call<AuthResponseDto> loginUser(@Body AuthRequestDto authRequestDto);
}
