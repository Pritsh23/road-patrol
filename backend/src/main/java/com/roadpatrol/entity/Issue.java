package com.roadpatrol.entity;

import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;




@Entity
@Table(name = "issues")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Issue extends BaseEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private IssueCategory category;

    private Double latitude;
    private Double longitude;

    @Enumerated(EnumType.STRING)
    private IssueStatus status;

    private Float severityScore;
    private Float priorityScore;

    private Boolean isDuplicate = false;
    private Boolean isSpam = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cluster_id")
    private IssueCluster cluster;
}
