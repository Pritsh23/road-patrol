package com.roadpatrol.entity;
import java.time.LocalDateTime;
import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "issue_status_history")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class IssueStatusHistory {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Issue issue;

    @Enumerated(EnumType.STRING)
    private IssueStatus oldStatus;

    @Enumerated(EnumType.STRING)
    private IssueStatus newStatus;

    private UUID changedBy;

    private LocalDateTime changedAt;
}