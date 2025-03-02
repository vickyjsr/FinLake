package com.finlake.controller;

import com.finlake.common.enums.ResponseCode;
import com.finlake.model.TransactionSplit;
import com.finlake.model.request.TransactionSplitDTO;
import com.finlake.model.response.FinlakeResponse;
import com.finlake.model.response.TransactionSplitResponse;
import com.finlake.repository.TransactionSplitRepository;
import com.finlake.service.BaseResponseService;
import com.finlake.service.TransactionSplitService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("/v1")
@RequestMapping("/v1/split")
@Tag(name = "6. Transaction Split Controller")
public class TransactionSplitController {

    private final TransactionSplitService transactionSplitService;
    private final BaseResponseService baseResponseService;

    public TransactionSplitController(TransactionSplitService transactionSplitService, BaseResponseService baseResponseService) {
        this.transactionSplitService = transactionSplitService;
        this.baseResponseService = baseResponseService;
    }

    @PostMapping("/new")
    public ResponseEntity<FinlakeResponse<TransactionSplitResponse>> saveTransactionSplits(@RequestBody TransactionSplitDTO transactionSplitDTO) {
        return baseResponseService.ok(transactionSplitService.createTransactionSplit(transactionSplitDTO), transactionSplitDTO.getRequestId(), ResponseCode.TRANSACTION_CREATED.getCode());
    }

    @GetMapping("/list")
    public ResponseEntity<FinlakeResponse<List<TransactionSplitResponse>>> getTransactionSplits(@RequestParam String financeRoomId, @RequestParam String requestId) {
        return baseResponseService.ok(transactionSplitService.getTransactionSplit(financeRoomId, requestId), requestId, ResponseCode.TRANSACTION_FETCHED.getCode());
    }

    @PostMapping("/settle")
    public ResponseEntity<FinlakeResponse<TransactionSplitResponse>> settleTransactionSplit(@RequestHeader String requestId, @RequestParam String transactionSplitId) {
        return baseResponseService.ok(transactionSplitService.updateTransactionSplit(requestId, transactionSplitId), requestId, ResponseCode.TRANSACTION_UPDATED.getCode());
    }
}
