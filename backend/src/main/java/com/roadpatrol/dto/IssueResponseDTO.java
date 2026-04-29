package com.roadpatrol.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.roadpatrol.entity.IssueCategory;
import com.roadpatrol.entity.IssueStatus;

import lombok.*;

@Getter @Setter @Builder
public class IssueResponseDTO {

    private UUID id;

    private String title;
    private String description;

    private IssueCategory category;

    private Double latitude;
    private Double longitude;

    private IssueStatus status;

    private Float severityScore;
    private Float priorityScore;

    private Boolean isDuplicate;
    private Boolean isSpam;

    private UUID clusterId;

    private int totalVotes;

    private LocalDateTime createdAt;

    private List<String> imageUrls;
}