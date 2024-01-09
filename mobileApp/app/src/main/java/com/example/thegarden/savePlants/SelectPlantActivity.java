package com.example.thegarden.savePlants;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.thegarden.MainActivity;
import com.example.thegarden.PlantId.RetrofitClient;
import com.example.thegarden.databinding.ActivitySelectPlantBinding;
import com.example.thegarden.dto.PlantSaveRequestDto;
import com.example.thegarden.dto.PlantSaveResponseDto;
import com.example.thegarden.network.ApiClient;
import com.example.thegarden.ui.errors.PlantNotRecognizedActivity;
import com.example.thegarden.ui.scan.ScanFragment;
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

        // Handle the back button event
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(SelectPlantActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Optionally, if you want to close the current activity
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);


        Intent intent = getIntent();
        if (intent != null) {
            plantInfoList = (ArrayList<PlantInfo>) intent.getSerializableExtra("plantInfoList");
            imageUri = intent.getParcelableExtra("capturedImageUri");
            Glide.with(this)
                    .load(imageUri)
                    .signature(new ObjectKey(System.currentTimeMillis())) // Use a unique signature to prevent caching issues
                    .into(binding.imageViewCaptured);
            if (plantInfoList != null) {
                adapter = new ViewPagerAdapter(plantInfoList);
                binding.viewPager.setAdapter(adapter);
                binding.viewPager.invalidate(); // Invalidate to refresh the view
                binding.viewPager.getAdapter().notifyDataSetChanged(); // Notify the adapter to refresh the items

            }
        }

        // Set OnClickListener for the save button
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePlantData();
            }
        });

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Change the behavior of the button based on the current page
                if (position != binding.viewPager.getAdapter().getItemCount() - 1) {
                    // For the first two pages, set the button to move to the next slide
                    binding.buttonWrongPlant.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Move to the next slide
                            binding.viewPager.setCurrentItem(position + 1, true);
                        }
                    });
                } else {
                    // On the third page, keep the original behavior of the button
                    binding.buttonWrongPlant.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Open a new intent as originally intended
                            Intent intent = new Intent(SelectPlantActivity.this, PlantNotRecognizedActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });



        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPrefs", MODE_PRIVATE);
        userEmail = sharedPreferences.getString("userEmail", null); // Default to 'null' if not found
        token = sharedPreferences.getString("AuthToken", null);

        // binding.viewPager.setOnClickListener(v -> savePlantData());


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
        // updateButtonVisibility();
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