package com.example.thegarden.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.thegarden.databinding.FragmentProfileBinding;
import com.example.thegarden.ui.scan.TutorialHowToScanActivity;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Use ViewModelProvider.AndroidViewModelFactory to create a ViewModelProvider
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                        .get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set up the logic for each CardView click
        setUpCardViewClickListeners();

        // Observe the name and email LiveData objects
        profileViewModel.getFirstName().observe(getViewLifecycleOwner(), name -> {
            binding.textViewProfileName.setText(name);
        });

        profileViewModel.getEmail().observe(getViewLifecycleOwner(), email -> {
            binding.textViewProfileEmail.setText(email);
        });

        return root;
    }
    private void setUpCardViewClickListeners() {
        // Assuming you have set the IDs for each CardView in your layout
        binding.cardViewSettingsProfile.setOnClickListener(v -> onSettingsClick());
        binding.cardViewContactUs.setOnClickListener(v -> onSettingsClick());
        binding.cardViewNotification.setOnClickListener(v -> onSettingsClick());
        binding.cardViewTakePhoto.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TutorialHowToScanActivity.class); // Replace NewActivity with the actual class name of the activity you want to open
            startActivity(intent);
        });
    }

    public void onSettingsClick() {
        // Example action: Showing a toast message
        Toast.makeText(getActivity(), "Navigating to home...", Toast.LENGTH_SHORT).show();

        // Here you would put your logic to navigate to the home page
        // For example, using NavController or another method depending on your navigation setup
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
