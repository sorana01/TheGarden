package com.example.thegarden.dto;

public class PlantSaveResponseDto {
    private boolean success;
    private String message;

    // Constructor, getters, and setters
    public PlantSaveResponseDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

