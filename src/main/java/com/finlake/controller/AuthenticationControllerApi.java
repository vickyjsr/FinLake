package com.finlake.controller;

import com.finlake.model.request.AuthenticationRequestDto;
import com.finlake.model.request.RegisterRequestDto;
import com.finlake.model.response.AuthenticationResponse;
import com.finlake.model.response.FinlakeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthenticationControllerApi {
    @PostMapping("/register")
    ResponseEntity<FinlakeResponse<AuthenticationResponse>> register(@RequestBody RegisterRequestDto request);

    @PostMapping("/login")
    ResponseEntity<FinlakeResponse<AuthenticationResponse>> authenticate(@RequestBody AuthenticationRequestDto request);
}
