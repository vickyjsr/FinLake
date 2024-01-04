package com.finlake.controller;

import com.finlake.model.TransactionSplit;
import com.finlake.repository.TransactionSplitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("/v1")
@RequestMapping("/v1")
public class TransactionSplitController {

    @Autowired
    private TransactionSplitRepository transactionSplitRepository;

    @PostMapping("/newTransactionSplit")
    public TransactionSplit saveTransactionSplits(@RequestBody TransactionSplit transactionSplit) {
        return transactionSplitRepository.save(transactionSplit);
    }

    @GetMapping("/listTransactionSplits")
    List<TransactionSplit> getTransactionSplits() {
        return transactionSplitRepository.findAll();
    }
}
