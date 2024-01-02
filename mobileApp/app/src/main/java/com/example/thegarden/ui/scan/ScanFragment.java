package com.example.thegarden.ui.scan;

import static com.example.thegarden.PlantId.IdentifyPlantUtil.convertBitmapToBase64;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.thegarden.PlantId.IdentifyPlantUtil;
import com.example.thegarden.PlantId.PlantIdentificationCallback;
import com.example.thegarden.databinding.FragmentScanBinding;
import com.example.thegarden.dto.PlantSaveRequestDto;
import com.example.thegarden.dto.PlantSaveResponseDto;
import com.example.thegarden.network.ApiClient;
import com.example.thegarden.savePlants.PlantApi;
import com.example.thegarden.savePlants.PlantInfo;
import com.example.thegarden.savePlants.SelectPlantActivity;
import com.example.thegarden.savePlants.ViewPagerAdapter;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.thegarden.MainActivity;
import com.example.thegarden.PlantId.IdentifyPlantUtil;
import com.example.thegarden.PlantId.PlantIdentificationCallback;
import com.example.thegarden.databinding.FragmentHomeBinding;
import com.example.thegarden.databinding.FragmentScanBinding;
import com.example.thegarden.savePlants.PlantInfo;
import com.example.thegarden.savePlants.SelectPlantActivity;

import java.io.Serializable;
import java.util.List;

public class ScanFragment extends Fragment {

    private FragmentScanBinding binding;
    private String userEmail;
    private ScanViewModel scanViewModel;
    private ViewPagerAdapter adapter; // Your adapter for the ViewPager
    private List<PlantInfo> plantInfoList; // The list to be populated with plant data

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        scanViewModel =
                new ViewModelProvider(this).get(ScanViewModel.class);

        binding = FragmentScanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        if (getActivity() != null) {
//            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE);
//            userEmail = sharedPreferences.getString("userEmail", null); // Default to 'null' if not found
//        }
//        else {
//            Log.d("ScanFragment", "No Activity bound");
//        }

        startCamera();

//        binding.buttonNext.setOnClickListener(v -> navigateSlide(true));
//        binding.buttonPrev.setOnClickListener(v -> navigateSlide(false));
        //updateButtonVisibility();

//        if (userEmail != null) {
//            // Add a listener for a button to save the plant data
//            binding.saveButton.setOnClickListener(v -> savePlantData());
//        } else {
//            Toast.makeText(getActivity(), "Email not found", Toast.LENGTH_SHORT).show();
//
//        }
        return root;
    }

//    private void navigateSlide(boolean next) {
//        int currentItem = binding.viewPager.getCurrentItem();
//        if (next) {
//            // Move to the next slide
//            if (currentItem < adapter.getItemCount() - 1) {
//                binding.viewPager.setCurrentItem(currentItem + 1);
//            }
//        } else {
//            // Move to the previous slide
//            if (currentItem > 0) {
//                binding.viewPager.setCurrentItem(currentItem - 1);
//            }
//        }
//        updateButtonVisibility();
//    }

//    private void updateButtonVisibility() {
//        int currentItem = binding.viewPager.getCurrentItem();
//        int itemCount = adapter.getItemCount();
//
//        // Show only the "Back" button on the first slide
//        if (currentItem == 0) {
//            binding.buttonPrev.setVisibility(View.GONE);
//            binding.buttonNext.setVisibility(View.VISIBLE);
//        }
//        // Show only the "Next" button on the last slide
//        else if (currentItem == itemCount - 1) {
//            binding.buttonNext.setVisibility(View.GONE);
//            binding.buttonPrev.setVisibility(View.VISIBLE);
//        }
//        // Show both buttons on all other slides
//        else {
//            binding.buttonNext.setVisibility(View.VISIBLE);
//            binding.buttonPrev.setVisibility(View.VISIBLE);
//        }
//    }

//    private void savePlantData() {
//        // Get the currently displayed PlantInfo
//        int currentPlantIndex = binding.viewPager.getCurrentItem();
//        PlantInfo currentPlant = plantInfoList.get(currentPlantIndex);
//
//        PlantSaveRequestDto requestDto = new PlantSaveRequestDto(currentPlant.getName(), currentPlant.getImageUrl(), userEmail);
//
//        // Create Retrofit API call
//        ApiClient retrofitService = new ApiClient();
//        PlantApi plantApi = retrofitService.getRetrofit().create(PlantApi.class);
//
//        plantApi.savePlant(requestDto).enqueue(new Callback<PlantSaveResponseDto>() {
//            @Override
//            public void onResponse(Call<PlantSaveResponseDto> call, Response<PlantSaveResponseDto> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    // Success response
//                    PlantSaveResponseDto plantResponse = response.body();
//                    Toast.makeText(getActivity(), plantResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                } else {
//                    // Unsuccessful response
//                    String errorMessage = "Failed to save plant";
//                    if (response.errorBody() != null) {
//                        try {
//                            // Parse the error response into PlantSaveResponseDto
//                            PlantSaveResponseDto errorResponse = new Gson().fromJson(response.errorBody().charStream(), PlantSaveResponseDto.class);
//                            errorMessage = errorResponse.getMessage();
//                        } catch (Exception e) {
//                            // Log the error or handle the parsing exception
//                            Log.e("SelectPlantActivity", "Error parsing error body", e);
//                        }
//                    }
//                    Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PlantSaveResponseDto> call, Throwable t) {
//                // Network error or other issues related to making the call
//                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

        private void startCamera() {
        dispatchTakePictureIntent();
        return root;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            takePictureLauncher.launch(takePictureIntent);
        }
    }


//    private void onPlantIdentified(List<PlantInfo> plantInfoList) {
//        this.plantInfoList = plantInfoList;
//        adapter = new ViewPagerAdapter(plantInfoList);
//        binding.viewPager.setAdapter(adapter);
//        binding.viewPager.setVisibility(View.VISIBLE); // Make ViewPager visible
//        binding.selectionButtons.setVisibility(View.VISIBLE); // Make buttons visible
//        updateButtonVisibility();
//    }


    private final ActivityResultLauncher<Intent> takePictureLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                Bundle extras = result.getData().getExtras();
                                Bitmap imageBitmap = (Bitmap) extras.get("data");
                                if (imageBitmap != null) {
                                    String base64Image = convertBitmapToBase64(imageBitmap);
                                    if (getContext() == null) {
                                        Log.d("SAVE IMG", "getContext() is null - unable to save imageUri");
                                    }
                                    Uri imageUri = IdentifyPlantUtil.saveImageToFile(getContext(), imageBitmap, "captured_image.jpg");

                                    // Use the modified identifyPlantFromBase64 method with a callback
                                    new IdentifyPlantUtil(getActivity()).identifyPlantFromBase64(base64Image, new PlantIdentificationCallback() {
                                        @Override
                                        public void onResult(List<PlantInfo> plantInfoList) {

                                            if (!plantInfoList.isEmpty() && imageUri!=null) {
                                                Log.d("IF LOOP", "Breaks here");
                                                Intent intent = new Intent(getActivity(), SelectPlantActivity.class);
                                                intent.putExtra("plantInfoList", (Serializable) plantInfoList);
                                                intent.putExtra("capturedImageUri", imageUri);
                                                // Grant temporary read permission to the Uri
                                                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                                            if (!plantInfoList.isEmpty()) {
                                                Intent intent = new Intent(getActivity(), SelectPlantActivity.class);
                                                intent.putExtra("plantInfoList", (Serializable) plantInfoList);
                                                startActivity(intent);
                                            } else {
                                                // Handle the case where no plants were identified or an error occurred
                                                // This could be showing a toast message or a dialog
                                            }
                                        }
                                    });

                                }
                            }
                        }
                    });

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}