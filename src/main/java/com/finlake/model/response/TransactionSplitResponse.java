package com.finlake.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionSplitResponse {
    private String requestId;
    String transactionSplitId;
    private String transactionId;
    private UserResponse paidByUser;
    private UserResponse receivedByUser;
    private String amount;
    private String splitPercent;
    private String status;
}
