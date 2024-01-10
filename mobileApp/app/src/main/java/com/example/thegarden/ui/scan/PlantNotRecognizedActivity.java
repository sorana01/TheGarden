package com.example.thegarden.ui.scan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thegarden.R;

public class PlantNotRecognizedActivity extends Activity {
    private Button buttonHowToScan;
    private Button buttonAddYourPlant;

    private Uri imageUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_not_recognized);

        buttonHowToScan = findViewById(R.id.buttonHowToScan);

        buttonAddYourPlant = findViewById(R.id.buttonAddYourPlant);

        buttonHowToScan.setOnClickListener(view -> {
            // Intent to start TutorialHowToScanActivity
            Log.d("Button Click", "How to scan button was clicked");
            Intent intent = new Intent(PlantNotRecognizedActivity.this, TutorialHowToScanActivity.class);
            startActivity(intent);
        });


        // Get the image URI passed from SelectPlantActivity
        String imageUriString = getIntent().getStringExtra("capturedImageUri");
        if (imageUriString != null) {
            imageUri = Uri.parse(imageUriString);
        }

        buttonAddYourPlant.setOnClickListener(view -> {
            // Inflate the custom layout
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_add_plant, null);

            // Create the AlertDialog
            AlertDialog dialog = new AlertDialog.Builder(PlantNotRecognizedActivity.this)
                    //.setTitle("Add Your Plant")
                    .setView(dialogView)
                    .create();

            // Find views and set the button click listener
            EditText editTextPlantName = dialogView.findViewById(R.id.editTextPlantName);
            Button buttonAddPlant = dialogView.findViewById(R.id.buttonAddPlant);

            buttonAddPlant.setOnClickListener(v -> {
                String plantName = editTextPlantName.getText().toString();
                if (!plantName.isEmpty() && imageUri != null) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("plantName", plantName);
                    resultIntent.putExtra("imageUri", imageUri.toString());
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(PlantNotRecognizedActivity.this, "Please enter a plant name.", Toast.LENGTH_SHORT).show();
                }
            });

            // Additional configurations for the dialog (optional)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // Make the dialog's background transparent
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); // Adjust the layout parameters

            // Show the AlertDialog
            dialog.show();
        });
    }






}