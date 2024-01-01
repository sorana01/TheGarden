package com.example.backendapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlantSaveRequestDto {
    private String name;
    private String imageUrl;
    private String userEmail;
}
