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

    private List<String> userIds;

    String requestId;
}
