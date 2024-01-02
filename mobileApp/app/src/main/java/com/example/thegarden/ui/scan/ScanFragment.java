package com.example.thegarden.ui.scan;

import static com.example.thegarden.PlantId.IdentifyPlantUtil.convertBitmapToBase64;

import android.app.Activity;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ScanViewModel scanViewModel =
                new ViewModelProvider(this).get(ScanViewModel.class);

        binding = FragmentScanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        dispatchTakePictureIntent();
        return root;
    }


    private void dispatchTakePictureIntent() {
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
                                    // Use the modified identifyPlantFromBase64 method with a callback
                                    new IdentifyPlantUtil(getActivity()).identifyPlantFromBase64(base64Image, new PlantIdentificationCallback() {
                                        @Override
                                        public void onResult(List<PlantInfo> plantInfoList) {
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