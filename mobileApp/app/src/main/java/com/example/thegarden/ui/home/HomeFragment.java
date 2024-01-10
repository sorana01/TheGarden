package com.example.thegarden.ui.home;

import android.os.Bundle;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.text.SpannableString;
import android.text.Spanned;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.thegarden.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Create an instance of the HomeViewModel
        HomeViewModel homeViewModel =
                new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                        .get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Observe the first name LiveData from the ViewModel
        homeViewModel.getFirstName().observe(getViewLifecycleOwner(), firstName -> {
            // Set the greeting message with the user's first name
            String greeting = "Hello " + firstName + ",\nHow are you today?";
            SpannableString spannableString = new SpannableString(greeting);

            // Apply the larger text size to "Hello [FirstName],"
            int endIndex = greeting.indexOf(',') + 1; // End index of the larger text
            spannableString.setSpan(new RelativeSizeSpan(1.5f), 0, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            binding.textHome.setText(spannableString);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
