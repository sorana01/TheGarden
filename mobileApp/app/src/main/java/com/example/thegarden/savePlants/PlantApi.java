package com.example.thegarden.savePlants;

import com.example.thegarden.dto.PlantSaveRequestDto;
import com.example.thegarden.dto.PlantSaveResponseDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PlantApi {
    @POST("api/plants")
    Call<PlantSaveResponseDto> savePlant(@Body PlantSaveRequestDto plantSaveRequestDto);

    @GET("api/plants")
    Call<List<PlantInfo>> getPlantsForCurrentUser();
}
