package com.roadpatrol.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.roadpatrol.entity.Escalation;
import org.springframework.data.repository.query.Param;

@Repository
public interface EscalationRepository
        extends JpaRepository<Escalation, UUID> {

    List<Escalation> findByIssueId(UUID issueId);

    @Query("""
        SELECT e FROM Escalation e
        WHERE e.status = 'SENT'
        AND e.createdAt < :threshold
    """)
    List<Escalation> findPendingEscalations(
            @Param("threshold")
            LocalDateTime threshold
    );
}