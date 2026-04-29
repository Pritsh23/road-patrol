package com.roadpatrol.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class AuthResponseDTO {

    private String token;
    private String email;
    private String role;
}
