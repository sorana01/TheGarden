package com.example.thegarden.PlantId;

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
    public List<String> getPlantNames() {
        List<String> plantNames = new ArrayList<>();
        if (result != null && result.getClassification() != null) {
            for (Suggestion suggestion : result.getClassification().getSuggestions()) {
                plantNames.add(suggestion.getName());
            }
        }
        return plantNames;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
