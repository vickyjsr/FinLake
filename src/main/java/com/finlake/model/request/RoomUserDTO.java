package com.finlake.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RoomUserDTO {
    private String requestId;

    private String financeRoomId;

    private String userId;

    private String status;
}
