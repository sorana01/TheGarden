package com.example.thegarden.PlantId;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PlantIDApi {
    @Headers("Api-Key: DxMHU11qdj0P45qivaonzuq0NVTEPWFW4NKEOpL2pPyv3Gtuii")
    @POST("https://plant.id/api/v3/identification")
    Call<PlantResponse> identifyPlant(@Body PlantRequest plantRequest);
}
