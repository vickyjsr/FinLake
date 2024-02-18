package com.finlake.controller;

import com.finlake.model.Transaction;
import com.finlake.repository.TransactionRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("/v1")
@RequestMapping("/v1")
@Tag(name = "5. Transaction Controller")
public class TransactionController {

    private final TransactionRepository transactionRepository;

    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @PostMapping("/newTransaction")
    public Transaction saveTransaction(@RequestBody Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @GetMapping("/listTransactions")
    List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }
}
