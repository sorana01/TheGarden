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
import org.springframework.stereotype.Service;

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

}
