package com.finlake.model.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

    @NotNull(message = "RequestId can not be null")
    @NotBlank(message = "RequestId can not be blank")
    @Size(min = 1, max = 50, message = "Invalid size of requestId")
    private String requestId;

    @NotNull(message = "name can not be null")
    @NotBlank(message = "name can not be blank")
    @Size(min = 1, max = 50, message = "Invalid size of name")
    private String name;

    @Size(min = 1, max = 50, message = "Invalid size of email")
    @NotNull(message = "Email can not be null")
    @NotBlank(message = "Email can not be blank")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$|")
    private String email;

    @NotNull(message = "Password can not be null")
    @NotBlank(message = "Password can not be blank")
    @Size(min = 8, max = 15, message = "Invalid size of password")
    private String password;

    @NotNull(message = "Mobile number can not be null")
    @NotBlank(message = "Mobile number can not be blank")
    @Size(min = 10, max = 10, message = "Invalid size of mobile number")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
    private String mobileNumber;
}
