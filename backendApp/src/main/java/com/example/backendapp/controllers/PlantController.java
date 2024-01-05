package com.example.backendapp.controllers;

import com.example.backendapp.dto.PlantSaveRequestDto;
import com.example.backendapp.dto.PlantSaveResponseDto;
import com.example.backendapp.exceptions.PlantSaveException;
import com.example.backendapp.exceptions.UserNotFoundException;
import com.example.backendapp.model.Plant;
import com.example.backendapp.repository.PlantRepository;
import com.example.backendapp.services.PlantService;
import com.example.backendapp.services.PlantServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/plants")
@RequiredArgsConstructor
public class PlantController {
    private final PlantService plantService;
    private static final Logger logger = LoggerFactory.getLogger(PlantController.class);

    @PostMapping
    public ResponseEntity<PlantSaveResponseDto> savePlant(@RequestBody PlantSaveRequestDto plantSaveRequest) {
        try {
            plantService.savePlant(plantSaveRequest);
            return ResponseEntity.ok(new PlantSaveResponseDto(true, "Plant saved successfully"));
        } catch (UserNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new PlantSaveResponseDto(false, e.getMessage()));
        } catch (PlantSaveException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PlantSaveResponseDto(false, "Failed to save plant. Please try again later."));
        }
    }

    @GetMapping
    public ResponseEntity<List<Plant>> getPlantsForCurrentUser(HttpServletRequest request) {
        // Extract the Authorization header
        String authorizationHeader = request.getHeader("Authorization");

        // Optionally, log the Authorization header to check the token received
        logger.info("Authorization Header: {}", authorizationHeader);

        // Continue with your existing logic
        List<Plant> plants = plantService.getPlantsForCurrentUser();
        return ResponseEntity.ok(plants);
    }

}
