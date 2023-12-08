package com.example.thegarden.PlantId;

import com.example.thegarden.savePlants.PlantInfo;

import java.util.List;

public interface PlantIdentificationCallback {
    void onResult(List<PlantInfo> plantInfoList);
}
