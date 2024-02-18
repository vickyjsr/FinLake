package com.finlake.model;

import com.finlake.model.response.UserResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FinanceRoomRequestData {
    private FinanceRoomBody financeRoom;
    private List<UserResponse> userList;
}
