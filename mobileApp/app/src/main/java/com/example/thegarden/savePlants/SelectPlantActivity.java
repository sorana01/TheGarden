package com.example.thegarden.savePlants;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.thegarden.PlantId.RetrofitClient;
import com.example.thegarden.databinding.ActivitySelectPlantBinding;
import com.example.thegarden.dto.PlantSaveRequestDto;
import com.example.thegarden.dto.PlantSaveResponseDto;
import com.example.thegarden.network.ApiClient;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectPlantActivity extends AppCompatActivity {

    private ActivitySelectPlantBinding binding;
    private ViewPagerAdapter adapter;
    private ArrayList<PlantInfo> plantInfoList;
    private Uri imageUri;

    private String userEmail;
    private String token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectPlantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if (intent != null) {
            plantInfoList = (ArrayList<PlantInfo>) intent.getSerializableExtra("plantInfoList");
            imageUri = intent.getParcelableExtra("capturedImageUri");
            Glide.with(this).load(imageUri).into(binding.imageViewCaptured);
            if (plantInfoList != null) {
                adapter = new ViewPagerAdapter(plantInfoList);
                binding.viewPager.setAdapter(adapter);
                binding.viewPager.invalidate(); // Invalidate to refresh the view
                binding.viewPager.getAdapter().notifyDataSetChanged(); // Notify the adapter to refresh the items

            }
        }

        binding.buttonNext.setOnClickListener(v -> navigateSlide(true));
        binding.buttonPrev.setOnClickListener(v -> navigateSlide(false));
        updateButtonVisibility();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPrefs", MODE_PRIVATE);
        userEmail = sharedPreferences.getString("userEmail", null); // Default to 'null' if not found
        token = sharedPreferences.getString("AuthToken", null);


        if (userEmail != null) {
            // Add a listener for a button to send the plant data
            binding.saveButton.setOnClickListener(v -> savePlantData());
        } else {
            Toast.makeText(SelectPlantActivity.this, "Email not found", Toast.LENGTH_SHORT).show();

        }

    }

    private void navigateSlide(boolean next) {
        int currentItem = binding.viewPager.getCurrentItem();
        if (next) {
            // Move to the next slide
            if (currentItem < adapter.getItemCount() - 1) {
                binding.viewPager.setCurrentItem(currentItem + 1);
            }
        } else {
            // Move to the previous slide
            if (currentItem > 0) {
                binding.viewPager.setCurrentItem(currentItem - 1);
            }
        }
        updateButtonVisibility();
    }

    private void updateButtonVisibility() {
        int currentItem = binding.viewPager.getCurrentItem();
        int itemCount = adapter.getItemCount();

        // Show only the "Back" button on the first slide
        if (currentItem == 0) {
            binding.buttonPrev.setVisibility(View.GONE);
            binding.buttonNext.setVisibility(View.VISIBLE);
        }
        // Show only the "Next" button on the last slide
        else if (currentItem == itemCount - 1) {
            binding.buttonNext.setVisibility(View.GONE);
            binding.buttonPrev.setVisibility(View.VISIBLE);
        }
        // Show both buttons on all other slides
        else {
            binding.buttonNext.setVisibility(View.VISIBLE);
            binding.buttonPrev.setVisibility(View.VISIBLE);
        }
    }

    private void savePlantData() {
        // Get the currently displayed PlantInfo
        int currentPlantIndex = binding.viewPager.getCurrentItem();
        PlantInfo currentPlant = plantInfoList.get(currentPlantIndex);

        PlantSaveRequestDto requestDto = new PlantSaveRequestDto(currentPlant.getName(), currentPlant.getImageUrl());
        Log.d("SharedPreferences(2)", "JWT Token: " + token);


        // Create Retrofit API call
        ApiClient retrofitService = new ApiClient();
        PlantApi plantApi = retrofitService.getRetrofit().create(PlantApi.class);

        plantApi.savePlant(requestDto, token).enqueue(new Callback<PlantSaveResponseDto>() {
            @Override
            public void onResponse(Call<PlantSaveResponseDto> call, Response<PlantSaveResponseDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Success response
                    PlantSaveResponseDto plantResponse = response.body();
                    Toast.makeText(SelectPlantActivity.this, plantResponse.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    // Unsuccessful response
                    String errorMessage = "Failed to save plant";
                    if (response.errorBody() != null) {
                        try {
                            // Parse the error response into PlantSaveResponseDto
                            PlantSaveResponseDto errorResponse = new Gson().fromJson(response.errorBody().charStream(), PlantSaveResponseDto.class);
                            errorMessage = errorResponse.getMessage();
                        } catch (Exception e) {
                            // Log the error or handle the parsing exception
                            Log.e("SelectPlantActivity", "Error parsing error body", e);
                        }
                    }
                    Toast.makeText(SelectPlantActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PlantSaveResponseDto> call, Throwable t) {
                // Network error or other issues related to making the call
                Toast.makeText(SelectPlantActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }
}