package com.finlake.model.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomUserResponse {
    private String id;
    private String requestId;
    private String financeRoomId;
    private UserResponse user;
    private String status;
}
