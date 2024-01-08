package com.example.thegarden.savePlants;

import com.example.thegarden.dto.PlantSaveRequestDto;
import com.example.thegarden.dto.PlantSaveResponseDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PlantApi {
    @POST("api/plants")
    Call<PlantSaveResponseDto> savePlant(@Body PlantSaveRequestDto plantSaveRequestDto, @Header("Authorization") String authToken);

    @GET("api/plants")
    Call<List<PlantInfo>> getPlantsForCurrentUser(@Header("Authorization") String authToken);

    @DELETE("api/plants/by-name/{name}")
    Call<Void> deletePlantByName(@Header("Authorization") String authToken, @Path("name") String plantName);


}
