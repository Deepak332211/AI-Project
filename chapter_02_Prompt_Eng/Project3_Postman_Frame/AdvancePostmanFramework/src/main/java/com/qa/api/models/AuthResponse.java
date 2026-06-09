package com.qa.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AuthResponse - POJO model for authentication response
 * Represents the response from login/authentication endpoint
 */
public class AuthResponse {

    @JsonProperty("token")
    private String token;

    /**
     * Default constructor
     */
    public AuthResponse() {
    }

    /**
     * Constructor with token
     * @param token Authentication token
     */
    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "token='" + token + '\'' +
                '}';
    }
}
