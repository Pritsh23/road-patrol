package com.roadpatrol.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.roadpatrol.dto.IssueRequestDTO;
import com.roadpatrol.dto.IssueResponseDTO;
import com.roadpatrol.entity.Issue;
import com.roadpatrol.entity.IssueCluster;
import com.roadpatrol.entity.IssueStatus;
import com.roadpatrol.entity.User;
import com.roadpatrol.repository.IssueClusterRepository;
import com.roadpatrol.repository.IssueRepository;
import com.roadpatrol.repository.UserRepository;
import com.roadpatrol.repository.VoteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final AIService aiService;
    private final IssueClusterRepository clusterRepository;
    private final VoteRepository voteRepository;

    // =====================================================
    // CREATE ISSUE
    // =====================================================

    @Override
    public IssueResponseDTO createIssue(IssueRequestDTO dto) {

        User user = getCurrentUser();

        Issue issue = Issue.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .category(dto.getCategory())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .status(IssueStatus.PENDING)
                .severityScore(5.0f)
                .isSpam(false)
                .user(user)
                .build();

        issueRepository.save(issue);

        // =========================
        // CLUSTERING
        // =========================

        IssueCluster cluster = assignToCluster(issue);
        issue.setCluster(cluster);

        // =========================
        // PRIORITY
        // =========================

        issue.setPriorityScore(
                calculatePriority(issue)
        );

        issueRepository.save(issue);

        return mapToDTO(issue);
    }

    // =====================================================
    // GET ISSUE BY ID
    // =====================================================

    @Override
    public IssueResponseDTO getIssueById(UUID id) {

        Issue issue = issueRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Issue not found"));

        return mapToDTO(issue);
    }

    // =====================================================
    // GET NEARBY ISSUES
    // =====================================================

    @Override
    public List<IssueResponseDTO> getNearbyIssues(
            double lat,
            double lng,
            double radius
    ) {

        List<Issue> issues =
                issueRepository.findNearbyIssues(
                        lat,
                        lng,
                        radius
                );

        return issues.stream()
                .map(this::mapToDTO)
                .toList();
    }

    // =====================================================
    // UPDATE ISSUE STATUS
    // =====================================================

    @Override
    public IssueResponseDTO updateIssueStatus(
            UUID issueId,
            IssueStatus status
    ) {

        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() ->
                        new RuntimeException("Issue not found"));

        issue.setStatus(status);

        issueRepository.save(issue);

        return mapToDTO(issue);
    }

    // =====================================================
    // CLUSTERING LOGIC
    // =====================================================

    private IssueCluster assignToCluster(Issue issue) {

        List<IssueCluster> nearbyClusters =
                clusterRepository.findNearbyClusters(
                        issue.getLatitude(),
                        issue.getLongitude(),
                        0.5
                );

        if (!nearbyClusters.isEmpty()) {

            IssueCluster cluster = nearbyClusters.get(0);

            cluster.setTotalReports(
                    cluster.getTotalReports() + 1
            );

            return clusterRepository.save(cluster);
        }

        // CREATE NEW CLUSTER

        IssueCluster newCluster = IssueCluster.builder()
                .centerLat(issue.getLatitude())
                .centerLng(issue.getLongitude())
                .category(issue.getCategory())
                .totalReports(1)
                .status(IssueStatus.PENDING)
                .build();

        return clusterRepository.save(newCluster);
    }

    // =====================================================
    // PRIORITY LOGIC
    // =====================================================

    private float calculatePriority(Issue issue) {

        int votes =
                voteRepository.countUpvotes(issue.getId());

        Float severity =
                issue.getSeverityScore();

        if (severity == null) {
            severity = 0f;
        }

        LocalDateTime createdAt =
                issue.getCreatedAt();

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }

        long ageHours =
                Duration.between(
                        createdAt,
                        LocalDateTime.now()
                ).toHours();

        return votes + severity + (ageHours * 0.1f);
    }

    // =====================================================
    // GET CURRENT USER
    // =====================================================

    private User getCurrentUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    // =====================================================
    // ENTITY → DTO
    // =====================================================

   private IssueResponseDTO mapToDTO(Issue issue) {

    UUID clusterId = null;

    if (issue.getCluster() != null) {
        clusterId = issue.getCluster().getId();
    }

    UUID userId = null;
    String userName = null;

    if (issue.getUser() != null) {
        userId = issue.getUser().getId();
        userName = issue.getUser().getName();
    }

    return IssueResponseDTO.builder()
            .id(issue.getId())
            .title(issue.getTitle())
            .description(issue.getDescription())
            .category(issue.getCategory())
            .latitude(issue.getLatitude())
            .longitude(issue.getLongitude())
            .status(issue.getStatus())
            .severityScore(issue.getSeverityScore())
            .priorityScore(issue.getPriorityScore())
            .isDuplicate(issue.getIsDuplicate())
            .isSpam(issue.getIsSpam())
            .clusterId(clusterId)
            .createdAt(issue.getCreatedAt())

            // USER DETAILS
            .userId(userId)
            .userName(userName)

            // AI DETAILS
            .aiVerified(issue.getAiVerified())
            .aiConfidenceScore(issue.getAiConfidenceScore())
            .aiDetectionLabel(issue.getAiDetectionLabel())
            .spamReason(issue.getSpamReason())

            .build();
}

    }
