package com.finlake.controller;

import com.finlake.model.request.AuthenticationRequestDTO;
import com.finlake.model.request.RegisterRequestDTO;
import com.finlake.model.response.AuthenticationResponse;
import com.finlake.model.response.FinlakeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthenticationControllerApi {
    @PostMapping("/register")
    ResponseEntity<FinlakeResponse<AuthenticationResponse>> register(@RequestBody RegisterRequestDTO request);

    @PostMapping("/login")
    ResponseEntity<FinlakeResponse<AuthenticationResponse>> authenticate(@RequestBody AuthenticationRequestDTO request);
}
