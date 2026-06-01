package com.roadpatrol.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.roadpatrol.dto.DashboardSummaryDTO;
import com.roadpatrol.dto.IssueResponseDTO;
import com.roadpatrol.dto.TopClusterDTO;
import com.roadpatrol.entity.Issue;
import com.roadpatrol.entity.IssueStatus;
import com.roadpatrol.repository.EscalationRepository;
import com.roadpatrol.repository.IssueClusterRepository;
import com.roadpatrol.repository.IssueRepository;
import com.roadpatrol.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl
        implements DashboardService {

    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final IssueClusterRepository clusterRepository;
    private final EscalationRepository escalationRepository;
   
    @Override
    public DashboardSummaryDTO getSummary() {

        return DashboardSummaryDTO.builder()
                .totalIssues(
                        issueRepository.count())
                .pendingIssues(
                        issueRepository.countByStatus(
                                IssueStatus.PENDING))
                .inProgressIssues(
                        issueRepository.countByStatus(
                                IssueStatus.IN_PROGRESS))
                .resolvedIssues(
                        issueRepository.countByStatus(
                                IssueStatus.RESOLVED))
                .totalUsers(
                        userRepository.count())
                .build();
    }

    @Override
    public Map<String, Long> getCategoryStats() {

        List<Object[]> results = issueRepository.countIssuesByCategory();

        Map<String, Long> stats = new HashMap<>();

        for (Object[] row : results) {

            stats.put(
                    row[0].toString(),
                    (Long) row[1]);
        }

        return stats;
    }

    @Override
    public List<TopClusterDTO> getTopClusters() {

        return clusterRepository
                .findTop5ByOrderByTotalReportsDesc()
                .stream()
                .map(cluster -> TopClusterDTO.builder()
                        .clusterId(cluster.getId())
                        .category(cluster.getCategory())
                        .totalReports(cluster.getTotalReports())
                        .centerLat(cluster.getCenterLat())
                        .centerLng(cluster.getCenterLng())
                        .priorityScore(cluster.getPriorityScore())
                        .build())
                .toList();
    }

    @Override
    public Map<String, Long> getEscalationStats() {

        List<Object[]> results = escalationRepository.countEscalationsByLevel();

        Map<String, Long> stats = new HashMap<>();

        for (Object[] row : results) {

            Integer level = (Integer) row[0];
            Long count = (Long) row[1];

            stats.put(
                    "LEVEL_" + level,
                    count);
        }

        return stats;
    }

    @Override
public List<IssueResponseDTO> getRecentIssues() {

    return issueRepository
            .findTop5ByOrderByCreatedAtDesc()
            .stream()
            .map(this::mapIssueToDTO)
            .toList();
}


private IssueResponseDTO mapIssueToDTO(
        Issue issue
) {

    return IssueResponseDTO.builder()
            .id(issue.getId())
            .title(issue.getTitle())
            .description(issue.getDescription())
            .category(issue.getCategory())
            .status(issue.getStatus())
            .createdAt(issue.getCreatedAt())
            .build();
}

}