package com.finlake.service;

import com.finlake.common.config.JwtService;
import com.finlake.common.enums.ResponseCode;
import com.finlake.common.enums.RoleType;
import com.finlake.common.exception.UserAlreadyExistsForEmailException;
import com.finlake.common.exception.UserDoesNotExistsForEmailException;
import com.finlake.common.helper.RequestValidator;
import com.finlake.model.User;
import com.finlake.model.request.AuthenticationRequestDTO;
import com.finlake.model.request.RegisterRequestDTO;
import com.finlake.model.response.AuthenticationResponse;
import com.finlake.model.response.FinlakeResponse;
import com.finlake.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final BaseResponseService baseResponseService;
    private final JwtService jwtService;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, BaseResponseService baseResponseService, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.baseResponseService = baseResponseService;
        this.jwtService = jwtService;
    }

    public ResponseEntity<FinlakeResponse<AuthenticationResponse>> register(RegisterRequestDTO request) {
        RequestValidator.validateRequest(request, request.getRequestId());
        User checkPresenceOfUser = userRepository.findByEmail(request.getEmail());
        if (checkPresenceOfUser != null) {
            throw new UserAlreadyExistsForEmailException(request.getRequestId(), ResponseCode.USER_ALREADY_EXISTS);
        }
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .mobileNumber(request.getMobileNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .roleType(RoleType.USER)
                .requestId(request.getRequestId())
                .build();
        String jwtToken = jwtService.generateToken(user);
        userRepository.save(user);
        AuthenticationResponse authenticationResponse = AuthenticationResponse
                .builder()
                .token(jwtToken)
                .requestId(request.getRequestId())
                .userId(user.getId())
                .requestId(request.getRequestId())
                .responseCode(ResponseCode.USER_REGISTERED.getCode())
                .build();
        return baseResponseService.ok(authenticationResponse, authenticationResponse.getRequestId(), authenticationResponse.getResponseCode());
    }

    public ResponseEntity<FinlakeResponse<AuthenticationResponse>> authenticate(AuthenticationRequestDTO request) {
        RequestValidator.validateRequest(request, request.getRequestId());
        User checkPresenceOfUser = userRepository.findByEmail(request.getEmail());
        if (checkPresenceOfUser == null) {
            throw new UserDoesNotExistsForEmailException(request.getRequestId(), ResponseCode.USER_DOES_NOT_EXIST.getCode());
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail());
        String jwtToken = jwtService.generateToken(user);
        AuthenticationResponse authenticationResponse = AuthenticationResponse
                .builder()
                .token(jwtToken)
                .requestId(request.getRequestId())
                .userId(user.getId())
                .requestId(request.getRequestId())
                .responseCode(ResponseCode.USER_AUTHORIZED.getCode())
                .build();
        return baseResponseService.ok(authenticationResponse, request.getRequestId(), authenticationResponse.getResponseCode());
    }
}
