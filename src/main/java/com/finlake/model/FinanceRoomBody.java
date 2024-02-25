package com.finlake.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FinanceRoomBody {
    private String name;

    private String createdBy;

    private String roomType;
}
