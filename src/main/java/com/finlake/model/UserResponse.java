package com.finlake.model;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResponse {
    private String id;

    private String name;

    private String email;

    private String password;

    private String mobileNumber;

    private String roleType;

    private String created_at;

    private String updated_at;
}
