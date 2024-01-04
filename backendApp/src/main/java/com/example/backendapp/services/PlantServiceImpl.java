package com.example.backendapp.services;

import com.example.backendapp.dto.PlantSaveRequestDto;
import com.example.backendapp.exceptions.PlantSaveException;
import com.example.backendapp.exceptions.UserNotFoundException;
import com.example.backendapp.model.Plant;
import com.example.backendapp.model.User;
import com.example.backendapp.repository.PlantRepository;
import com.example.backendapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantServiceImpl implements PlantService{
    private final PlantRepository plantRepository;

    private final UserRepository userRepository; // Assuming a UserRepository exists

    @Override
    public void savePlant(PlantSaveRequestDto plantSaveRequest) {
        User user = userRepository.findByEmail(plantSaveRequest.getUserEmail());
        if (user == null) {
            throw new UserNotFoundException("Save failed: User not found.");
        }

        try {
            Plant plant = new Plant(plantSaveRequest.getName(), plantSaveRequest.getImageUrl(), user);
            plantRepository.save(plant);
        } catch (Exception e) {
            throw new PlantSaveException("Save failed: Unable to save plant. Details: " + e.getMessage());
        }
    }

    @Override
    public List<Plant> getPlantsForCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName(); // This gets the email of the logged-in user
        User user = userRepository.findByEmail(userEmail);
        Long userId = user.getId();
        return plantRepository.findByUserId(userId);
    }

}
