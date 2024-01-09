package com.example.backendapp.services;

import com.example.backendapp.dto.PlantSaveRequestDto;
import com.example.backendapp.exceptions.PlantSaveException;
import com.example.backendapp.exceptions.UserNotFoundException;
import com.example.backendapp.model.Plant;
import com.example.backendapp.model.User;
import com.example.backendapp.repository.PlantRepository;
import com.example.backendapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantServiceImpl implements PlantService{
    private final PlantRepository plantRepository;
    private final UserRepository userRepository; // Assuming a UserRepository exists
    private static final Logger logger = LoggerFactory.getLogger(PlantServiceImpl.class);

    @Override
    public void savePlant(PlantSaveRequestDto plantSaveRequest) {
        String userEmail = getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException("Save failed: User not found.");
        }

        // Get all plants for the current user
        List<Plant> userPlants = getPlantsForCurrentUser();

        // Check if a plant with the same name already exists for the user
        for (Plant existingPlant : userPlants) {
            if (existingPlant.getName().equalsIgnoreCase(plantSaveRequest.getName())) {
                throw new PlantSaveException("Save failed: Plant with the same name already exists for this user.");
            }
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
        String userEmail = getCurrentUserEmail();
        logger.info("User details - userEmail: {}", userEmail);
        User user = userRepository.findByEmail(userEmail);
        logger.info("User details - ID: {}, Email: {}, First Name: {}, Last Name: {}", user.getId(), user.getEmail(),user.getFirstName(), user.getLastName());
        Long userId = user.getId();
        return plantRepository.findByUserId(userId);
    }

    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("Authentication information not available");
        }
        return authentication.getName();
    }


    @Override
    public void deletePlant(String plantName) throws Exception {
        String userEmail = getCurrentUserEmail();
        User user = userRepository.findByEmail(userEmail);

        if (user == null) {
            throw new UserNotFoundException("Delete failed: User not found.");
        }

        // Change to find by name and user
        Plant plant = findByNameAndUserId(plantName, user.getId());

        if (plant == null) {
            throw new IllegalStateException("Delete failed: Plant not found or does not belong to the current user.");
        }

        try {
            plantRepository.delete(plant);
            logger.info("Plant deleted successfully - Name: {}", plant.getName());
        } catch (Exception e) {
            throw new Exception("Delete failed: Unable to delete plant. Details: " + e.getMessage());
        }
    }


    // Helper method to check if plant exists and belongs to the user
    private Plant findByNameAndUserId(String plantName, Long userId) {
        return plantRepository.findByNameAndUserId(plantName, userId);
    }

}
