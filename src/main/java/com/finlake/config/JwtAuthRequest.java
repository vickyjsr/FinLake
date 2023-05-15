package com.finlake.config;

import lombok.Data;

@Data
public class JwtAuthRequest {

    String email, password;

    public JwtAuthRequest() {

    }

    public JwtAuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
