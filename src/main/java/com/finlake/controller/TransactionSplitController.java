package com.finlake.controller;

import com.finlake.model.TransactionSplit;
import com.finlake.repository.TransactionSplitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:8080")
public class TransactionSplitController {

    @Autowired
    private TransactionSplitRepository transactionSplitRepository;

    @PostMapping("/newTransactionSplit")
    public TransactionSplit saveFinanceRoom(@RequestBody TransactionSplit transactionSplit) {
        return transactionSplitRepository.save(transactionSplit);
    }

    @GetMapping("/listTransactionSplits")
    List<TransactionSplit> getUsers() {
        return transactionSplitRepository.findAll();
    }
}
