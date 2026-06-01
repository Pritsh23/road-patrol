package com.roadpatrol.service;

import java.util.List;
import java.util.Map;

import com.roadpatrol.dto.DashboardSummaryDTO;
import com.roadpatrol.dto.IssueResponseDTO;
import com.roadpatrol.dto.TopClusterDTO;

public interface DashboardService {

    DashboardSummaryDTO getSummary();
    Map<String, Long> getCategoryStats();
    List<TopClusterDTO> getTopClusters();
Map<String, Long> getEscalationStats();
List<IssueResponseDTO> getRecentIssues();
}