package com.finlake.model.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FinanceRoomRequestDTO {
    private String financeRoomName;

    private String createdBy;

    private String roomType;

    private String status;

    private List<String> userIds;

    private String requestId;
}
