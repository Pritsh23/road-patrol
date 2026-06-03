package com.roadpatrol.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roadpatrol.dto.DashboardSummaryDTO;
import com.roadpatrol.dto.IssueResponseDTO;
import com.roadpatrol.dto.TopClusterDTO;
import com.roadpatrol.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/summary")
    public DashboardSummaryDTO getSummary() {

        return dashboardService.getSummary();
    }

    @GetMapping("/category-stats")
public Map<String, Long> getCategoryStats() {

    return dashboardService.getCategoryStats();
}
@GetMapping("/top-clusters")
public List<TopClusterDTO> getTopClusters() {

    return dashboardService.getTopClusters();
}

@GetMapping("/escalation-stats")
public Map<String, Long> getEscalationStats() {

    return dashboardService.getEscalationStats();
}

@GetMapping("/recent-issues")
public List<IssueResponseDTO> getRecentIssues() {

    return dashboardService.getRecentIssues();
}
}