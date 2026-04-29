package com.roadpatrol.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class CommentResponseDTO {

    private UUID id;
    private UUID userId;
    private String userName;

    private String content;

    private LocalDateTime createdAt;
}
