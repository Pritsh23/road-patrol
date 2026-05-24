package com.roadpatrol.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

@Getter @Setter
@Data
public class RegisterRequestDTO {

    @NotBlank
    private String name;

    @Email
    private String email;

    @Size(min = 6)
    private String password;
}
