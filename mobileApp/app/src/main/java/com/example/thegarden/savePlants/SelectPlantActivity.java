package com.example.thegarden.savePlants;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thegarden.databinding.ActivitySelectPlantBinding;

import java.util.ArrayList;

public class SelectPlantActivity extends AppCompatActivity {

    private ActivitySelectPlantBinding binding;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectPlantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if (intent != null) {
            ArrayList<PlantInfo> plantInfoList = (ArrayList<PlantInfo>) intent.getSerializableExtra("plantInfoList");
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

}