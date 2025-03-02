package com.finlake.model.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private String id;
    private String requestId;
    private String name;
    private String description;
    private String amount;
    private String status;
    private UserResponse paidByUser;
    private String financeRoomId;
    private List<TransactionSplitResponse> splits;
}
