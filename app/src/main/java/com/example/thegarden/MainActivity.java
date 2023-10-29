package com.example.thegarden;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.thegarden.PlantId.PlantIDApi;
import com.example.thegarden.PlantId.PlantRequest;
import com.example.thegarden.PlantId.PlantResponse;
import com.example.thegarden.PlantId.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.thegarden.databinding.ActivityMainBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static PlantIDApi plantIDApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.plant.id")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        plantIDApi = RetrofitClient.getInstance();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        binding.yourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                identifyPlant();
            }
        });
    }

    private void identifyPlant() {
        String base64Image = convertImageToBase64();
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
                    Toast.makeText(MainActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MainActivity.this, "API call failed", Toast.LENGTH_SHORT).show();
                Log.e("PlantID", "API call failed", t);
            }
        });
    }

    private String convertImageToBase64() {
        // Load the image from drawable resource
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.plant1);

        // Convert the Bitmap to ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        // Convert the ByteArrayOutputStream to byte array
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        // Convert byte array to Base64 string
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

}