package com.example.thegarden.savePlants;

import java.io.Serializable;

public class PlantInfo implements Serializable {
    private String name;
    private String imageUrl;

    public PlantInfo(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
