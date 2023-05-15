package com.finlake.config;

import lombok.Data;

@Data
public class JwtAuthResponse {

    private String token;

    public JwtAuthResponse() {

    }

    public JwtAuthResponse(String token) {
        this.token = token;
    }
}
