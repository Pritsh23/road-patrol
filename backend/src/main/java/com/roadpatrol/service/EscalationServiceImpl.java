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

        List<Issue> pendingIssues = issueRepository.findByStatus(IssueStatus.PENDING);

        for (Issue issue : pendingIssues) {

            long hours = Duration.between(
                    issue.getCreatedAt(),
                    LocalDateTime.now()
            ).toHours();

            if (hours > 48) {

                Escalation escalation = Escalation.builder()
                        .issue(issue)
                        .escalationLevel(1)
                        .escalatedTo("municipal@city.gov")
                        .status(EscalationStatus.SENT)
                        .build();

                escalationRepository.save(escalation);
            }
        }
    }
}
