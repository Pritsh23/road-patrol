package com.roadpatrol.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DashboardSummaryDTO {

    private long totalIssues;

    private long pendingIssues;

    private long inProgressIssues;

    private long resolvedIssues;

    private long totalUsers;
}