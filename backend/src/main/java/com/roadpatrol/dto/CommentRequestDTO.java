package com.roadpatrol.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;
@Getter @Setter
public class CommentRequestDTO {

    @NotNull
    private UUID issueId;

    @NotBlank
    private String content;
}
