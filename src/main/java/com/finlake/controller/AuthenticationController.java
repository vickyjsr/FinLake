package com.finlake.controller;

import com.finlake.model.request.AuthenticationRequestDTO;
import com.finlake.model.response.AuthenticationResponse;
import com.finlake.service.AuthenticationService;
import com.finlake.model.request.RegisterRequestDTO;
import com.finlake.model.response.FinlakeResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("/v1")
@RequestMapping("/v1/auth")
@Tag(name = "1. Authentication Controller")
public class AuthenticationController implements AuthenticationControllerApi {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public ResponseEntity<FinlakeResponse<AuthenticationResponse>> register(RegisterRequestDTO request) {
        return authenticationService.register(request);
    }

    @Override
    public ResponseEntity<FinlakeResponse<AuthenticationResponse>> authenticate(AuthenticationRequestDTO request) {
        return authenticationService.authenticate(request);
    }
}
