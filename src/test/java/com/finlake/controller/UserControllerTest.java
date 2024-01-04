package com.finlake.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.finlake.enums.RoleType;
import com.finlake.model.User;
import com.finlake.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    MockMvc mockMvc;

    @Mock
    UserRepository userRepository;

    ObjectMapper objectMapper = new ObjectMapper(); // json to string and vice versa
    ObjectWriter objectWriter = objectMapper.writer();

    User user1, user2;
    List<User> users;

    @InjectMocks
    UserController userController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        user1 = User.builder().id("1").name("Gourav Modi").email("gourav@gmail.com")
                .password("123456").mobileNumber("8529452679").roleType(RoleType.USER) // Replace with the appropriate role
                .build();

        user2 = User.builder().id("2").name("John doe").email("john@gmail.com")
                .password("123456").mobileNumber("1234567890").roleType(RoleType.USER) // Replace with the appropriate role
                .build();

        users = Arrays.asList(user1, user2);
    }

    @Test
    public void testGetUsers() throws Exception {

        Mockito.when(userRepository.findAllUsers(Mockito.any(Pageable.class)))
                .thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/listAllUsers")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(users.size()));
    }

    @Test
    public void testFindAllUsersFiltered() throws Exception {
        String userId = "1";
        List<User> filteredUsers = Arrays.asList(user1);

        Mockito.when(userRepository.findAllUsersFiltered(Mockito.eq(userId), Mockito.any(Pageable.class)))
                .thenReturn(filteredUsers);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/listAllUsersFiltered")
                        .param("id", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(filteredUsers.size()));
    }
}
