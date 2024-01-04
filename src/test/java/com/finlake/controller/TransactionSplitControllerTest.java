package com.finlake.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.finlake.enums.RoleType;
import com.finlake.enums.RoomType;
import com.finlake.model.FinanceRoom;
import com.finlake.model.Transaction;
import com.finlake.model.TransactionSplit;
import com.finlake.model.User;
import com.finlake.repository.TransactionSplitRepository;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionSplitControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    TransactionSplitController transactionSplitController;

    @Mock
    TransactionSplitRepository transactionSplitRepository;

    TransactionSplit transactionSplit1, transactionSplit2;
    Transaction transaction;
    User user1, user2;
    List<TransactionSplit> transactionSplits;
    FinanceRoom financeRoom;
    Gson gson = new Gson();

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionSplitController).build();

        user1 = User.builder().id("1").name("Gourav Modi").email("gourav@gmail.com")
                .password("123456").mobileNumber("8529452679").roleType(RoleType.USER)
                .build();

        user2 = User.builder().id("2").name("John doe").email("john@gmail.com")
                .password("123456").mobileNumber("1234567890").roleType(RoleType.USER)
                .build();

        financeRoom = FinanceRoom.builder()
                .id("1").name("Room1").created_by(user1).room_type(RoomType.GROUP).status("active").build();

        transaction = Transaction.builder()
                .id("1")
                .amount("1000")
                .description("evening snacks")
                .finance_room(financeRoom)
                .paid_by_user(user1)
                .status("active")
                .name("snacks").build();

        transactionSplit1 = TransactionSplit.builder()
                .id("1")
                .transaction(transaction)
                .status("active")
                .user(user1)
                .amount("500")
                .build();

        transactionSplit2 = TransactionSplit.builder()
                .id("2")
                .transaction(transaction)
                .status("active")
                .user(user2)
                .amount("500")
                .build();

        transactionSplits = new ArrayList<>(List.of(transactionSplit1, transactionSplit2));
    }

    @Test
    public void saveTransactionSplits_test() throws Exception {
        Mockito.when(transactionSplitRepository.save(any(TransactionSplit.class))).thenReturn(transactionSplit1);

        String jsonString = gson.toJson(transactionSplit1);

        System.out.println(jsonString);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/newTransactionSplit")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(transactionSplit1.getId()));
    }

    @Test
    public void getTransactionSplits_test() throws Exception{
        Mockito.when(transactionSplitRepository.findAll()).thenReturn(transactionSplits);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/listTransactionSplits")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(transactionSplits.size()));
    }
}
