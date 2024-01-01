package com.example.thegarden.ui.plants;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.thegarden.databinding.FragmentHomeBinding;
import com.example.thegarden.databinding.FragmentPlantsBinding;
import com.example.thegarden.ui.plants.PlantsViewModel;

public class PlantsFragment extends Fragment {

    private FragmentPlantsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PlantsViewModel plantsViewModel =
                new ViewModelProvider(this).get(PlantsViewModel.class);

        binding = FragmentPlantsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textPlants;
        plantsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}