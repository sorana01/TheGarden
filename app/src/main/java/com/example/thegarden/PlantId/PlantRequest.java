package com.example.thegarden.PlantId;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlantRequest {
    @SerializedName("images")
    private List<String> images;
    @SerializedName("similar_images")
    private boolean similarImages;

    public PlantRequest(List<String> images, boolean similarImages) {
        this.images = images;
        this.similarImages = similarImages;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public boolean isSimilarImages() {
        return similarImages;
    }

    public void setSimilarImages(boolean similarImages) {
        this.similarImages = similarImages;
    }
}
