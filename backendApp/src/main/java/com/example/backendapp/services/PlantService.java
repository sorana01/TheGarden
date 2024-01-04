package com.example.backendapp.services;

import com.example.backendapp.dto.PlantSaveRequestDto;
import com.example.backendapp.model.Plant;

import java.util.List;

public interface PlantService {
    void savePlant(PlantSaveRequestDto plantSaveRequest);

    List<Plant> getPlantsForCurrentUser();
}
