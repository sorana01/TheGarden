package com.example.thegarden.PlantId;

import com.example.thegarden.savePlants.PlantInfo;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PlantResponse {
    @SerializedName("result")
    private PlantResult result;

    public PlantResult getResult() {
        return result;
    }

    public void setResult(PlantResult result) {
        this.result = result;
    }

    // Helper method to get a list of plant names
    public List<PlantInfo> getPlantDetails() {
        List<PlantInfo> plantDetails = new ArrayList<>();
        if (result != null && result.getClassification() != null) {
            for (Suggestion suggestion : result.getClassification().getSuggestions()) {
                String imageUrl = null;

                // Check if the list of similar images is not null and not empty
                if (suggestion.getSimilarImages() != null && !suggestion.getSimilarImages().isEmpty()) {
                    imageUrl = suggestion.getSimilarImages().get(0).getUrl(); // Get the URL of the first image
                }

                PlantInfo info = new PlantInfo(suggestion.getName(), imageUrl);
                plantDetails.add(info);
            }
        }
        return plantDetails;
    }

}

class PlantResult {
    @SerializedName("classification")
    private Classification classification;

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }
}

class Classification {
    @SerializedName("suggestions")
    private List<Suggestion> suggestions;

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }
}

class Suggestion {
    @SerializedName("name")
    private String name;
    @SerializedName("similar_images")
    private List<SimilarImage> similarImages;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getters and setters for similarImages
    public List<SimilarImage> getSimilarImages() {
        return similarImages;
    }

    public void setSimilarImages(List<SimilarImage> similarImages) {
        this.similarImages = similarImages;
    }
}

class SimilarImage {
    @SerializedName("url")
    private String url;

    // Getters and setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

