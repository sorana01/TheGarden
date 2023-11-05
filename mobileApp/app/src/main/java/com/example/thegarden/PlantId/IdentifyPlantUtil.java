package com.example.thegarden.PlantId;

import static com.example.thegarden.MainActivity.plantIDApi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IdentifyPlantUtil {
    private static Context context;
    public IdentifyPlantUtil(Context context) {
        this.context = context;
    }

    public static void identifyPlant(int resourceId) {
        String base64Image = convertImageToBase64(resourceId);
        List<String> images = Collections.singletonList(base64Image);

        PlantRequest request = new PlantRequest(images, true);
        Call<PlantResponse> call = plantIDApi.identifyPlant(request);

        call.enqueue(new Callback<PlantResponse>() {
            @Override
            public void onResponse(Call<PlantResponse> call, Response<PlantResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PlantResponse plantResponse = response.body();
                    List<String> plantNames = plantResponse.getPlantNames();

                    // Log the results
                    for (String plantName : plantNames) {
                        Log.d("PlantID", "Identified plant: " + plantName);
                    }
                } else {
                    // Handle error
                    Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT).show();
                    try {
                        Log.e("PlantID", "Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        Log.e("PlantID", "Error reading errorBody", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<PlantResponse> call, Throwable t) {
                // Handle the error
                Toast.makeText(context, "API call failed", Toast.LENGTH_SHORT).show();
                Log.e("PlantID", "API call failed", t);
            }
        });
    }

    public void identifyPlantFromBase64(String base64Image) {
        List<String> images = Collections.singletonList(base64Image);

        PlantRequest request = new PlantRequest(images, true);
        Call<PlantResponse> call = plantIDApi.identifyPlant(request);

        call.enqueue(new Callback<PlantResponse>() {
            @Override
            public void onResponse(Call<PlantResponse> call, Response<PlantResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PlantResponse plantResponse = response.body();
                    List<String> plantNames = plantResponse.getPlantNames();

                    // Log the results
                    for (String plantName : plantNames) {
                        Log.d("PlantID", "Identified plant: " + plantName);
                    }
                } else {
                    // Handle error
                    Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT).show();
                    try {
                        Log.e("PlantID", "Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        Log.e("PlantID", "Error reading errorBody", e);
                    }
                }
            }


            @Override
            public void onFailure(Call<PlantResponse> call, Throwable t) {
                // Handle the error
                Toast.makeText(context, "API call failed", Toast.LENGTH_SHORT).show();
                Log.e("PlantID", "API call failed", t);
            }
        });
    }


    private static String convertImageToBase64(int resourceId) {
        // Load the image from drawable resource
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);

        // Convert the Bitmap to ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        // Convert the ByteArrayOutputStream to byte array
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        // Convert byte array to Base64 string
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
