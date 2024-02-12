package com.finlake.controller;

import com.finlake.auth.AuthenticationRequest;
import com.finlake.auth.AuthenticationResponse;
import com.finlake.auth.AuthenticationService;
import com.finlake.auth.RegisterRequest;
import com.finlake.model.response.FinlakeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("/v1")
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<FinlakeResponse<AuthenticationResponse>> register(@RequestBody RegisterRequest request) {
        return authenticationService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<FinlakeResponse<AuthenticationResponse>> authenticate(@RequestBody AuthenticationRequest request) {
        return authenticationService.authenticate(request);
    }

}
