package com.roadpatrol.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;
@Getter @Setter
public class LoginRequestDTO {

    @Email
    private String email;

    @NotBlank
    private String password;
}
