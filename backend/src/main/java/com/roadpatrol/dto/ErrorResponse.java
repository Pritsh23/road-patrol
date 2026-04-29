package com.roadpatrol.dto;
import java.time.LocalDateTime;

import lombok.*;

@Getter @Setter @Builder
public class ErrorResponse {

    private String message;
    private int status;
    private LocalDateTime timestamp;
}
