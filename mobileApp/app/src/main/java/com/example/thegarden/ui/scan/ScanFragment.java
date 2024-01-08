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

import androidx.activity.OnBackPressedCallback;
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

        // Handle the back button event
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back press event
                startCamera();
            }
        });

        startCamera();
        return root;
    }

        private void startCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            takePictureLauncher.launch(takePictureIntent);
        }
    }

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