package com.roadpatrol.dto;
import com.roadpatrol.entity.IssueCategory;

import lombok.*;

@Getter @Setter @Builder
public class AIResponseDTO {

    private IssueCategory detectedCategory;
    private float severityScore;
    private boolean isSpam;
    private boolean isDuplicate;
}
