package com.roadpatrol.entity;

import java.util.List;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "issue_clusters")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class IssueCluster extends BaseEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private Double centerLat;
    private Double centerLng;

    @Enumerated(EnumType.STRING)
    private IssueCategory category;

    private Integer totalReports;
    private Float severityAvg;
    private Float priorityScore;

    @Enumerated(EnumType.STRING)
    private IssueStatus status;

    @OneToMany(mappedBy = "cluster", fetch = FetchType.LAZY)
    private List<Issue> issues;
}
