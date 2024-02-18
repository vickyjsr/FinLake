package com.finlake.model.response;

import lombok.*;

import java.sql.Timestamp;

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

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
