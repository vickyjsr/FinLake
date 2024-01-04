package com.finlake.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.finlake.enums.RoleType;
import com.finlake.enums.RoomType;
import com.finlake.model.FinanceRoom;
import com.finlake.model.RoomUser;
import com.finlake.model.User;
import com.finlake.repository.RoomUserRepository;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class RoomUserControllerTest {

    @Mock
    private RoomUserRepository roomUserRepository;

    MockMvc mockMvc;

    // json to string and vice versa
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @InjectMocks
    RoomUserController roomUserController;

    List<RoomUser> roomUsers;
    User user1, user2, user3;
    FinanceRoom financeRoom;
    RoomUser roomUser1, roomUser2, roomUser3;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(roomUserController).build();
        user1 = User.builder().id("1").name("Gourav Modi").email("gourav@gmail.com")
                .password("123456").mobileNumber("8529452679").roleType(RoleType.USER)
                .build();

        user2 = User.builder().id("2").name("John doe").email("john@gmail.com")
                .password("123456").mobileNumber("1234567890").roleType(RoleType.USER)
                .build();

        user3 = User.builder().id("3").name("Doe").email("doe@gmail.com")
                .password("123456").mobileNumber("9181991100").roleType(RoleType.USER)
                .build();

        financeRoom = FinanceRoom.builder()
                        .id("1").name("Room1").created_by(user1).room_type(RoomType.GROUP).status("active")
                .build();

        roomUser1 = RoomUser.builder().
                id("1").finance_room(financeRoom).user(user1).status("active").build();

        roomUser2 = RoomUser.builder().
                id("2").finance_room(financeRoom).user(user2).status("active").build();

        roomUser3 = RoomUser.builder().
                id("3").finance_room(financeRoom).user(user3).status("active").build();

        roomUsers = new ArrayList<>(List.of(roomUser1, roomUser2, roomUser3));
    }

    @Test
    public void saveRoomUser_Test() throws Exception {
        Mockito.when(roomUserRepository.save(roomUser1)).thenReturn(roomUser1);

        Gson gson = new Gson();
        String jsonString = gson.toJson(roomUser1);
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/newRoomUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(roomUser1.getId()));
    }

    @Test
    public void getRoomUsers_test() throws Exception {
        when(roomUserRepository.findAll()).thenReturn(roomUsers);

        mockMvc.perform(get("/v1/listAllRoomUsers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(roomUsers.size()));
    }

    @Test
    public void filterUserFromRoomUser_test() throws Exception {
        String user_id = "1";
        List<RoomUser> filteredRoomUsers = new ArrayList<>(List.of(roomUser1));
        when(roomUserRepository.findAllByUser_Id(user_id)).thenReturn(filteredRoomUsers);

        mockMvc.perform(get("/v1/filterUserFromRoomUser")
                        .param("id", user_id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(filteredRoomUsers.size()));
    }

    @Test
    public void filterFinanceRoomFromRoomUser_pagination_false_test() throws Exception {
        boolean pagination = false;
        String id = "1";
        String status = "active";
        List<User> users = new ArrayList<>(List.of(user1, user2, user3));
        when(roomUserRepository.findAllByFinanceRoom_Id(id, status)).thenReturn(users);

        mockMvc.perform(get("/v1/filterFinanceRoomFromRoomUser")
                .param("id", id).param("status", status)
                .param("pagination", String.valueOf(pagination))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(roomUsers.size()));
    }

    @Test
    public void filterFinanceRoomFromRoomUser_pagination_true_test() throws Exception {
        boolean pagination = true;
        String id = "1";
        String status = "active";
        int page = 1;
        int pageSize = 2;
        List<User> users = new ArrayList<>(List.of(user1, user2));
        List<Sort.Order> sorting = new ArrayList<>();
        sorting.add(new Sort.Order(Sort.Direction.ASC, "user.name"));
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorting));
        when(roomUserRepository.findAllByFinanceRoom_IdAndPaginate(id, status, pageable)).thenReturn(users);

        mockMvc.perform(get("/v1/filterFinanceRoomFromRoomUser")
                        .param("id", id).param("status", status)
                        .param("pagination", String.valueOf(pagination))
                        .param("page", String.valueOf(page))
                        .param("pageSize", String.valueOf(pageSize))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(users.size()));
    }
}

