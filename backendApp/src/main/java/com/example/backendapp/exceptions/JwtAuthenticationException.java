package com.example.backendapp.exceptions;

public class JwtAuthenticationException extends RuntimeException{
    public JwtAuthenticationException(final String ex){
        super(ex);
    }
}
