package com.finlake.auth;

import com.finlake.config.JwtService;
import com.finlake.constants.Constant;
import com.finlake.enums.RoleType;
import com.finlake.model.User;
import com.finlake.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.bcel.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        User checkPresenceOfUser = userRepository.findByEmail(request.getEmail());
        if (checkPresenceOfUser != null) {
            return AuthenticationResponse.builder().errorMessage(Constant.USER_EXIST).build();
        }
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .mobileNumber(request.getMobile_number())
                .password(passwordEncoder.encode(request.getPassword()))
                .roleType(RoleType.USER)
                .build();
        String jwtToken = jwtService.generateToken(user);
        userRepository.save(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .user_id(user.getId())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User checkPresenceOfUser = userRepository.findByEmail(request.getEmail());
        if (checkPresenceOfUser == null) {
            return AuthenticationResponse.builder().errorMessage(Constant.USER_DOES_NOT_EXIST).build();
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail());
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .user_id(user.getId())
                .build();
    }
}
