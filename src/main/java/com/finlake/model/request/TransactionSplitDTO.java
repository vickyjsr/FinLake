package com.finlake.model.request;

import jakarta.annotation.Nullable;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionSplitDTO {
    private String requestId;
    private String transactionId;
    private String paidByUserId;
    private String receivedByUserId;
    private String amount;
    private String splitPercent;
    private String status;
}
