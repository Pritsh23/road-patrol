package com.roadpatrol.entity;

import java.util.*;
import jakarta.persistence.*;
import lombok.*;




@Entity
@Table(name = "votes",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "issue_id"}))
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Vote {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Issue issue;

    @Enumerated(EnumType.STRING)
    private VoteType voteType;
}
