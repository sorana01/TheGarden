package com.example.thegarden.dto;


public class PlantSaveRequestDto {
    private String name;
    private String imageUrl;

    private String userEmail; // Use email to identify the user

    // Constructor
    public PlantSaveRequestDto(String name, String imageUrl, String userEmail) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.userEmail = userEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
