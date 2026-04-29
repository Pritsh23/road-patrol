package com.roadpatrol.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.roadpatrol.entity.EscalationStatus;

import lombok.*;
@Getter @Setter @Builder
public class EscalationResponseDTO {

    private UUID issueId;
    private int escalationLevel;
    private String escalatedTo;
    private EscalationStatus status;
    private LocalDateTime escalatedAt;
}
