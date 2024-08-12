package com.finlake.model.response;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private String id;

    private String name;

    private String email;

    private String mobileNumber;
}
