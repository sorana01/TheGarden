package com.example.thegarden.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.thegarden.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set up the logic for each CardView click
        setUpCardViewClickListeners();

        return root;
    }

    private void setUpCardViewClickListeners() {
        // Assuming you have set the IDs for each CardView in your layout
        binding.cardViewSettingsProfile.setOnClickListener(v -> onSettingsClick());
        binding.cardViewContactUs.setOnClickListener(v -> onSettingsClick());
        binding.cardViewNotification.setOnClickListener(v -> onSettingsClick());
        binding.cardViewTakePhoto.setOnClickListener(v -> onSettingsClick());
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
