package com.roadpatrol.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.roadpatrol.entity.Escalation;
import com.roadpatrol.entity.EscalationStatus;
import com.roadpatrol.entity.Issue;
import com.roadpatrol.entity.IssueStatus;
import com.roadpatrol.repository.EscalationRepository;
import com.roadpatrol.repository.IssueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EscalationServiceImpl implements EscalationService {

    private final IssueRepository issueRepository;
    private final EscalationRepository escalationRepository;

    @Override
    public void checkAndEscalateIssues() {

        List<Issue> pendingIssues =
                issueRepository.findByStatus(
                        IssueStatus.PENDING
                );

        for (Issue issue : pendingIssues) {

            long hours =
                    Duration.between(
                            issue.getCreatedAt(),
                            LocalDateTime.now()
                    ).toHours();

            int requiredLevel =
                    determineEscalationLevel(hours);

            // NO ESCALATION NEEDED

            if (requiredLevel == 0) {
                continue;
            }

            List<Escalation> escalations =
                    escalationRepository.findByIssueId(
                            issue.getId()
                    );

            int currentLevel =
                    escalations.stream()
                            .mapToInt(Escalation::getEscalationLevel)
                            .max()
                            .orElse(0);

            // ALREADY ESCALATED

            if (currentLevel >= requiredLevel) {
                continue;
            }

            Escalation escalation =
                    Escalation.builder()
                            .issue(issue)
                            .escalationLevel(requiredLevel)
                            .escalatedTo(
                                    determineAuthority(requiredLevel)
                            )
                            .status(EscalationStatus.SENT)
                            .responseAt(null)
                            .build();

            escalationRepository.save(escalation);

            // SIMULATED AUTHORITY EMAIL

            System.out.println(
                    "Escalation Level "
                            + requiredLevel
                            + " sent for issue "
                            + issue.getId()
            );
        }
    }

    // =========================
    // DETERMINE LEVEL
    // =========================

    private int determineEscalationLevel(long hours) {

        if (hours >= 96) {
            return 4;
        }

        if (hours >= 72) {
            return 3;
        }

        if (hours >= 48) {
            return 2;
        }

        if (hours >= 24) {
            return 1;
        }

        return 0;
    }

    // =========================
    // DETERMINE AUTHORITY
    // =========================

    private String determineAuthority(int level) {

        return switch (level) {

            case 1 -> "ward-officer@city.gov";

            case 2 -> "engineer@city.gov";

            case 3 -> "commissioner@city.gov";

            default -> "head-office@city.gov";
        };
    }
}