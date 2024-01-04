package com.finlake.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.finlake.enums.RoleType;
import com.finlake.enums.RoomType;
import com.finlake.model.*;
import com.finlake.repository.FinanceRoomRepository;
import com.finlake.repository.RoomUserRepository;
import com.finlake.repository.UserRepository;
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
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class FinanceRoomControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    FinanceRoomController financeRoomController;

    @Mock
    FinanceRoomRepository financeRoomRepository;

    @Mock
    RoomUserRepository roomUserRepository;

    @Mock
    UserRepository userRepository;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    UserResponse userResponse;
    FinanceRoom financeRoom;
    List<FinanceRoom> financeRooms;
    User user1;
    RoomUser roomUser1;
    List<RoomUser> roomUsers;
    List<User> users;
    FinanceRoomRequestData financeRoomRequestData;
    FinanceRoomBody financeRoomBody;
    List<UserResponse> userResponses;
    Gson gson = new Gson();

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(financeRoomController).build();
        userResponse = new UserResponse("1", "John Doe", "john.doe@example.com", "password", "1234567890", "USER", "2023-11-23 12:34:56", "2023-11-23 12:34:56");

        user1 = User.builder().id("1").name("Gourav Modi").email("gourav@gmail.com")
                .password("123456").mobileNumber("8529452679").roleType(RoleType.USER)
                .build();

        financeRoom = FinanceRoom.builder()
                .id("1").name("Room1").created_by(user1).room_type(RoomType.GROUP).status("active").created_at(Timestamp.valueOf("2023-11-23 12:34:56")).updated_at(Timestamp.valueOf("2023-11-23 12:34:56")).build();

        financeRooms = new ArrayList<>(List.of(financeRoom));

        roomUser1 = RoomUser.builder().
                id("1").finance_room(financeRoom).user(user1).status("active").build();

        roomUsers = new ArrayList<>(List.of(roomUser1));
        users = new ArrayList<>(List.of(user1));
        financeRoomBody = new FinanceRoomBody(financeRoom.getName(), financeRoom.getCreated_by().getId(), financeRoom.getRoom_type().getValue());
        userResponse = UserResponse.builder()
                .name(user1.getName())
                .id(user1.getId())
                .email(user1.getEmail())
                .mobileNumber(user1.getMobileNumber())
                .password(user1.getPassword())
                .build();
        userResponses = new ArrayList<>(List.of(userResponse));
        financeRoomRequestData = new FinanceRoomRequestData(financeRoomBody, userResponses);
    }

    @Test
    public void getFinanceRoomName_positive_test() throws Exception {
        String id = "1";
        Mockito.when(financeRoomRepository.findById(id)).thenReturn(Optional.ofNullable(financeRoom));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/get_finance_room_name")
                .param("id", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(financeRoom.getName()));
    }

    @Test
    public void getFinanceRoomName_negative_test() throws Exception {
        String id = "111";
        Mockito.when(financeRoomRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/get_finance_room_name")
                        .param("id", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void getAllFinanceRooms_pagination_false_test() throws Exception {
        int page = 1;
        int pageSize = 3;
        boolean pagination = false;
        String status = "active";

        Mockito.when(financeRoomRepository.findAllByStatus(status)).thenReturn(financeRooms);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/listAllFinanceRooms")
                        .param("page", String.valueOf(page))
                        .param("pageSize", String.valueOf(pageSize))
                        .param("pagination", String.valueOf(pagination))
                        .param("status", status)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(financeRooms.size()));
    }

    @Test
    public void getAllFinanceRooms_pagination_true_test() throws Exception {
        int page = 1;
        int pageSize = 10;
        boolean pagination = true;
        String status = "active";
        List<Sort.Order> sorting = new ArrayList<>();
        sorting.add(new Sort.Order(Sort.Direction.ASC, "updated_at"));
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorting));

        Mockito.when(financeRoomRepository.findAllByStatusAndPagination(status, pageable)).thenReturn(financeRooms);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/listAllFinanceRooms")
                        .param("page", String.valueOf(page))
                        .param("pageSize", String.valueOf(pageSize))
                        .param("pagination", String.valueOf(pagination))
                        .param("status", status)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void filterUserFromFinanceRoom_pagination_true_test() throws Exception {
        int page = 1;
        int pageSize = 10;
        boolean pagination = true;
        String status = "active";
        String user_id = "1";

        List<Sort.Order> sorting = new ArrayList<>();
        sorting.add(new Sort.Order(Sort.Direction.ASC, "updated_at"));
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorting));

        Mockito.when(roomUserRepository.findAllByUser_IdAndStatusAndPaginate(user_id, status, pageable)).thenReturn(roomUsers);
        List<String> financeRoomIds = new ArrayList<>();
        for (RoomUser roomUser : roomUsers) {
            financeRoomIds.add(roomUser.getFinance_room().getId());
        }
        Mockito.when(financeRoomRepository.findAllByIdAndStatus(financeRoomIds, status)).thenReturn(financeRooms);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/filterUserFromFinanceRoom")
                        .param("page", String.valueOf(page))
                        .param("pageSize", String.valueOf(pageSize))
                        .param("pagination", String.valueOf(pagination))
                        .param("status", status)
                        .param("id", user_id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(financeRooms.size()));
    }

    @Test
    public void filterUserFromFinanceRoom_pagination_false_test() throws Exception {
        int page = 1;
        int pageSize = 10;
        boolean pagination = false;
        String status = "active";
        String user_id = "1";

        Mockito.when(roomUserRepository.findAllByUser_IdAndStatus(user_id, status)).thenReturn(roomUsers);
        List<String> financeRoomIds = new ArrayList<>();
        for (RoomUser roomUser : roomUsers) {
            financeRoomIds.add(roomUser.getFinance_room().getId());
        }
        Mockito.when(financeRoomRepository.findAllByIdAndStatus(financeRoomIds, status)).thenReturn(financeRooms);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/filterUserFromFinanceRoom")
                        .param("page", String.valueOf(page))
                        .param("pageSize", String.valueOf(pageSize))
                        .param("pagination", String.valueOf(pagination))
                        .param("status", status)
                        .param("id", user_id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(financeRooms.size()));
    }

    @Test
    public void saveFinanceRoom_request_body_null_test() throws Exception {
        String jsonString = gson.toJson(financeRoomRequestData);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/newFinanceRoom")
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void saveFinanceRoom_request_body_present_room_user_empty_test() throws Exception {
        String jsonString = gson.toJson(financeRoomRequestData);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/newFinanceRoom")
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void saveFinanceRoom_request_body_present_room_user_present_test() throws Exception {
        String jsonString = gson.toJson(financeRoomRequestData);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/newFinanceRoom")
                        .content(jsonString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
