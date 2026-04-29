package com.roadpatrol.dto;

import java.util.UUID;

import com.roadpatrol.entity.IssueCategory;
import com.roadpatrol.entity.IssueStatus;

import lombok.*;

@Getter @Setter @Builder
public class ClusterResponseDTO {

    private UUID clusterId;

    private Double centerLat;
    private Double centerLng;

    private IssueCategory category;

    private int totalReports;

    private float severityAvg;
    private float priorityScore;

    private IssueStatus status;
}