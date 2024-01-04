package com.finlake.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finlake.auth.AuthenticationRequest;
import com.finlake.auth.AuthenticationResponse;
import com.finlake.auth.AuthenticationService;
import com.finlake.auth.RegisterRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationControllerTest {

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper(); // json to string and vice versa

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    AuthenticationController authenticationController;

    private RegisterRequest registerRequest;
    private AuthenticationRequest authenticationRequest;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
        registerRequest = new RegisterRequest("Gourav", "gouravmodi10@gmail.com", "123456", "8529452679");
        authenticationRequest = new AuthenticationRequest("gouravmodi10@gmail.com", "123456");
    }

    @Test
    public void register_success() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk());
    }

    // Add similar tests for other methods, for example, authenticate_success()

     @Test
     public void authenticate_success() throws Exception {
         mockMvc.perform(MockMvcRequestBuilders.post("/v1/login")
                 .contentType(MediaType.APPLICATION_JSON)
                 .accept(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsString(authenticationRequest)))
                 .andExpect(status().isOk());
     }
}
