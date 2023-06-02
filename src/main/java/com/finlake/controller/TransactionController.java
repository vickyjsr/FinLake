package com.finlake.controller;

import com.finlake.model.Transaction;
import com.finlake.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("/v1")
@RequestMapping("/v1")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping("/newTransaction")
    public Transaction saveFinanceRoom(@RequestBody Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @GetMapping("/listTransactions")
    List<Transaction> getUsers() {
        return transactionRepository.findAll();
    }


}
