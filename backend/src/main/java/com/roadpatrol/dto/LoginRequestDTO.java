package com.roadpatrol.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;
@Getter @Setter
@Data
public class LoginRequestDTO {

    @Email
    private String email;

    @NotBlank
    private String password;
}
