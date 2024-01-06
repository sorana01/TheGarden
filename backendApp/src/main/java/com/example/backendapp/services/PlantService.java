package com.example.backendapp.services;

import com.example.backendapp.dto.PlantSaveRequestDto;

public interface PlantService {
    void savePlant(PlantSaveRequestDto plantSaveRequest);
}
