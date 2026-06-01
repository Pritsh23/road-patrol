package com.roadpatrol.dto;

import java.util.UUID;

import com.roadpatrol.entity.IssueCategory;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TopClusterDTO {

    private UUID clusterId;

    private IssueCategory category;

    private Integer totalReports;

    private Double centerLat;

    private Double centerLng;

    private Float priorityScore;
}
