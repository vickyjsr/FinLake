package com.finlake.model.request;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionDTO {
    private String requestId;
    private String financeRoomId;
    private String status;
    private String description;
    private String amount;
    private String paidByUserId;
    private List<TransactionSplitDTO> split;
    private String name;
}
