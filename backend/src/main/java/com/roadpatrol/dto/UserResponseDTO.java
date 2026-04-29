package com.roadpatrol.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class UserResponseDTO {

    private UUID id;
    private String name;
    private String email;
    private String role;
    private int trustScore;
}
