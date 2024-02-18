package com.finlake.controller;

import com.finlake.model.TransactionSplit;
import com.finlake.repository.TransactionSplitRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("/v1")
@RequestMapping("/v1")
@Tag(name = "6. Transaction Split Controller")
public class TransactionSplitController {

    private final TransactionSplitRepository transactionSplitRepository;

    public TransactionSplitController(TransactionSplitRepository transactionSplitRepository) {
        this.transactionSplitRepository = transactionSplitRepository;
    }

    @PostMapping("/newTransactionSplit")
    public TransactionSplit saveTransactionSplits(@RequestBody TransactionSplit transactionSplit) {
        return transactionSplitRepository.save(transactionSplit);
    }

    @GetMapping("/listTransactionSplits")
    List<TransactionSplit> getTransactionSplits() {
        return transactionSplitRepository.findAll();
    }
}
