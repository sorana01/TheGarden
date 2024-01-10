package com.example.thegarden.PlantId;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PlantIDApi {
    @Headers("Api-Key: pF88C56lNUM8TIwkZzszPFQZO2z0a4i1NhPKfkhrupQd5zn4MA")
    @POST("https://plant.id/api/v3/identification")
    Call<PlantResponse> identifyPlant(@Body PlantRequest plantRequest);
}
