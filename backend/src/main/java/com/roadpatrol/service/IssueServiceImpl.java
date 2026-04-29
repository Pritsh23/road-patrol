package com.roadpatrol.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.roadpatrol.dto.AIResponseDTO;
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

    // =========================
    // CREATE ISSUE
    // =========================
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
                .user(user)
                .build();

        // 🤖 AI Processing
        AIResponseDTO aiResult = aiService.analyzeIssue(dto);
        issue.setSeverityScore(aiResult.getSeverityScore());
        issue.setIsSpam(aiResult.isSpam());

        // 🧠 Clustering
        IssueCluster cluster = assignToCluster(issue);
        issue.setCluster(cluster);

        // 💾 Save first (to get ID)
        issueRepository.save(issue);

        // 🔥 Priority Calculation
        issue.setPriorityScore(calculatePriority(issue));

        // 💾 Save again with priority
        issueRepository.save(issue);

        return mapToDTO(issue);
    }

    // =========================
    // GET ISSUE BY ID
    // =========================
    @Override
    public IssueResponseDTO getIssueById(UUID id) {

        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        return mapToDTO(issue);
    }

    // =========================
    // GET NEARBY ISSUES
    // =========================
    @Override
    public List<IssueResponseDTO> getNearbyIssues(double lat, double lng, double radius) {

        List<Issue> issues = issueRepository.findNearbyIssues(lat, lng, radius);

        return issues.stream()
                .map(this::mapToDTO)
                .toList();
    }

    // =========================
    // UPDATE STATUS
    // =========================
    @Override
    public IssueResponseDTO updateIssueStatus(UUID issueId, IssueStatus status) {

        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        issue.setStatus(status);

        issueRepository.save(issue);

        return mapToDTO(issue);
    }

    // =========================
    // CLUSTERING LOGIC
    // =========================
    private IssueCluster assignToCluster(Issue issue) {

        List<IssueCluster> nearbyClusters =
                clusterRepository.findNearbyClusters(
                        issue.getLatitude(),
                        issue.getLongitude(),
                        0.5 // 500 meters
                );

        if (!nearbyClusters.isEmpty()) {
            IssueCluster cluster = nearbyClusters.get(0);
            cluster.setTotalReports(cluster.getTotalReports() + 1);
            return clusterRepository.save(cluster);
        }

        IssueCluster newCluster = IssueCluster.builder()
                .centerLat(issue.getLatitude())
                .centerLng(issue.getLongitude())
                .category(issue.getCategory())
                .totalReports(1)
                .status(IssueStatus.PENDING)
                .build();

        return clusterRepository.save(newCluster);
    }

    // =========================
    // PRIORITY CALCULATION
    // =========================
    private float calculatePriority(Issue issue) {

        int votes = voteRepository.countUpvotes(issue.getId());
        float severity = issue.getSeverityScore() != null ? issue.getSeverityScore() : 0;

        long ageHours = Duration.between(
                issue.getCreatedAt(),
                LocalDateTime.now()
        ).toHours();

        return votes + severity + (ageHours * 0.1f);
    }

    // =========================
    // GET CURRENT USER (TEMP)
    // =========================
    private User getCurrentUser() {
        return userRepository.findByEmail("test@gmail.com")
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // =========================
    // ENTITY → DTO MAPPING
    // =========================
    private IssueResponseDTO mapToDTO(Issue issue) {

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
                .clusterId(issue.getCluster() != null ? issue.getCluster().getId() : null)
                .createdAt(issue.getCreatedAt())
                .build();
    }
}