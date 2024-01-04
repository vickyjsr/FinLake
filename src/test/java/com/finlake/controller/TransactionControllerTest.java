package com.finlake.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.finlake.enums.RoleType;
import com.finlake.enums.RoomType;
import com.finlake.model.FinanceRoom;
import com.finlake.model.Transaction;
import com.finlake.model.TransactionSplit;
import com.finlake.model.User;
import com.finlake.repository.TransactionRepository;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    TransactionController transactionController;

    @Mock
    TransactionRepository transactionRepository;

    Transaction transaction1, transaction2;
    User user1, user2;
    List<Transaction> transactions;
    FinanceRoom financeRoom;

    Gson gson = new Gson();

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();

        user1 = User.builder().id("1").name("Gourav Modi").email("gourav@gmail.com")
                .password("123456").mobileNumber("8529452679").roleType(RoleType.USER)
                .build();

        user2 = User.builder().id("2").name("John doe").email("john@gmail.com")
                .password("123456").mobileNumber("1234567890").roleType(RoleType.USER)
                .build();

        financeRoom = FinanceRoom.builder()
                .id("1").name("Room1").created_by(user1).room_type(RoomType.GROUP).status("active").build();

        transaction1 = Transaction.builder()
                .id("1")
                .amount("1000")
                .description("evening snacks")
                .finance_room(financeRoom)
                .paid_by_user(user1)
                .status("active")
                .name("snacks").build();

        transaction2 = Transaction.builder()
                .id("1")
                .amount("1000")
                .description("evening snacks")
                .finance_room(financeRoom)
                .paid_by_user(user2)
                .status("active")
                .name("snacks").build();

        transactions = new ArrayList<>(List.of(transaction1, transaction2));
    }

    @Test
    public void saveTransaction_test() throws Exception {
        Mockito.when(transactionRepository.save(transaction1)).thenReturn(transaction1);

        String jsonString = gson.toJson(transaction1);
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/newTransaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(transaction1.getId()));
    }

    @Test
    public void getTransactions_test() throws Exception{
        Mockito.when(transactionRepository.findAll()).thenReturn(transactions);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/listTransactions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(transactions.size()));
    }

}
