package com.example.thegarden.ui.errors;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thegarden.R;

public class PlantNotRecognizedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_not_recognized);

        Button buttonOptionOne = findViewById(R.id.buttonOptionOne);
        Button buttonOptionTwo = findViewById(R.id.buttonOptionTwo);

        buttonOptionOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Option 1 click
            }
        });

        buttonOptionTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Option 2 click
            }
        });
    }
}
