package com.finlake.controller;

import com.finlake.common.enums.ResponseCode;
import com.finlake.model.Transaction;
import com.finlake.model.request.TransactionDTO;
import com.finlake.model.response.FinlakeResponse;
import com.finlake.model.response.TransactionResponse;
import com.finlake.repository.TransactionRepository;
import com.finlake.service.BaseResponseService;
import com.finlake.service.TransactionService;
import com.finlake.service.TransactionServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("/v1")
@RequestMapping("/v1/transaction")
@Tag(name = "5. Transaction Controller")
public class TransactionController {

    private final TransactionService transactionService;
    private final BaseResponseService baseResponseService;

    public TransactionController(TransactionServiceImpl transactionService, BaseResponseService baseResponseService) {
        this.transactionService = transactionService;
        this.baseResponseService = baseResponseService;
    }

    @PostMapping("/new")
    public ResponseEntity<FinlakeResponse<TransactionResponse>> saveTransaction(@RequestBody TransactionDTO transaction) {
        TransactionResponse transactionResponse = transactionService.createTransactionRecord(transaction);
        return baseResponseService.ok(transactionResponse, transaction.getRequestId(), ResponseCode.TRANSACTION_CREATED.getCode());
    }

    @GetMapping("/list/{financeRoomId}")
    public ResponseEntity<FinlakeResponse<List<TransactionResponse>>> getAllTransactionRecordByFinanceRoomId(@PathVariable String financeRoomId, @RequestParam String requestId) {
        List<TransactionResponse> transactionResponses = transactionService.getAllTransactionRecordByFinanceRoomId(financeRoomId, requestId);
        return baseResponseService.ok(transactionResponses, requestId, ResponseCode.TRANSACTION_FETCHED.getCode());
    }
}
