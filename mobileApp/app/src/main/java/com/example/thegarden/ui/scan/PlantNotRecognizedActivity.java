package com.example.thegarden.ui.scan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.thegarden.R;

public class PlantNotRecognizedActivity extends Activity {
    private TextView textView;
    private Button buttonOptionOne;
    private Button buttonOptionTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to your XML layout
        setContentView(R.layout.activity_plant_not_recognized);

        // Initialize your TextView and Buttons
//        textView = (TextView) findViewById(R.id.textView);
        buttonOptionOne = (Button) findViewById(R.id.buttonOptionOne);
        buttonOptionTwo = (Button) findViewById(R.id.buttonOptionTwo);

        // Set up a click listener for buttonOptionOne
        buttonOptionOne.setOnClickListener(view -> {
            // Do something when buttonOptionOne is clicked
            textView.setText("Option 1 was clicked!");
        });

        // Set up a click listener for buttonOptionTwo
        buttonOptionTwo.setOnClickListener(view -> {
            Intent intent = new Intent(this, TutorialHowToScanActivity.class);
            startActivity(intent);
        });
    }
}
