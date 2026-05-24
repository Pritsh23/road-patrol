package com.roadpatrol.dto;

import lombok.*;

@Data
@Builder
public class AIResponseDTO {

    private String detectedCategory;

    private Float severityScore;

    private Boolean spam;

    private Float confidenceScore;

    private String spamReason;
}