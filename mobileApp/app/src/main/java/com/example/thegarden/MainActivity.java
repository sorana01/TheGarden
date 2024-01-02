package com.example.thegarden;

import static com.example.thegarden.PlantId.IdentifyPlantUtil.convertBitmapToBase64;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.example.thegarden.PlantId.IdentifyPlantUtil;
import com.example.thegarden.PlantId.PlantIDApi;
import com.example.thegarden.PlantId.PlantIdentificationCallback;
import com.example.thegarden.PlantId.RetrofitClient;
import com.example.thegarden.savePlants.PlantInfo;
import com.example.thegarden.savePlants.SelectPlantActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.thegarden.databinding.ActivityMainBinding;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static PlantIDApi plantIDApi;
    private IdentifyPlantUtil identifyPlant;

    private List<PlantInfo> plantInfoList;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        plantIDApi = RetrofitClient.getInstance();
        identifyPlant = new IdentifyPlantUtil(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_plants, R.id.navigation_scan, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }

//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            takePictureLauncher.launch(takePictureIntent);
//        }
//    }
//
//    private final ActivityResultLauncher<Intent> takePictureLauncher =
//            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
//                    new ActivityResultCallback<ActivityResult>() {
//                        @Override
//                        public void onActivityResult(ActivityResult result) {
//                            if (result.getResultCode() == Activity.RESULT_OK) {
//                                Bundle extras = result.getData().getExtras();
//                                Bitmap imageBitmap = (Bitmap) extras.get("data");
//                                if (imageBitmap != null) {
//                                    String base64Image = convertBitmapToBase64(imageBitmap);
//                                    // Use the modified identifyPlantFromBase64 method with a callback
//                                    new IdentifyPlantUtil(MainActivity.this).identifyPlantFromBase64(base64Image, new PlantIdentificationCallback() {
//                                        @Override
//                                        public void onResult(List<PlantInfo> plantInfoList) {
//                                            if (!plantInfoList.isEmpty()) {
//                                                Intent intent = new Intent(MainActivity.this, SelectPlantActivity.class);
//                                                intent.putExtra("plantInfoList", (Serializable) plantInfoList);
//                                                startActivity(intent);
//                                            } else {
//                                                // Handle the case where no plants were identified or an error occurred
//                                                // This could be showing a toast message or a dialog
//                                            }
//                                        }
//                                    });
//
//                                }
//                            }
//                        }
//                    });
    }
    
}
