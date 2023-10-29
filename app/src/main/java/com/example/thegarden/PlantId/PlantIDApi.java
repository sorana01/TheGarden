package com.example.thegarden.PlantId;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PlantIDApi {
    @Headers("Api-Key: a1SyPAFMAX1N8rmpQSsD7bucjzvp4cpc5ywuYguVSoeIspshU9")
    @POST("https://plant.id/api/v3/identification")
    Call<PlantResponse> identifyPlant(@Body PlantRequest plantRequest);
}
