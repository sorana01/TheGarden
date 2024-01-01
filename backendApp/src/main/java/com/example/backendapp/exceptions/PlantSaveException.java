package com.example.backendapp.exceptions;

public class PlantSaveException extends RuntimeException{
    public PlantSaveException(String message) {
        super(message);
    }

    public PlantSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
