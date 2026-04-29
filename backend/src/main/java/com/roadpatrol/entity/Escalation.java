package com.roadpatrol.entity;
import java.time.LocalDateTime;
import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "escalations")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Escalation extends BaseEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Issue issue;

    private Integer escalationLevel;

    private String escalatedTo;

    @Enumerated(EnumType.STRING)
    private EscalationStatus status;

    private LocalDateTime responseAt;
}
