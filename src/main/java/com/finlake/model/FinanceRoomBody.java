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

    private String created_by;

    private String room_type;
}
