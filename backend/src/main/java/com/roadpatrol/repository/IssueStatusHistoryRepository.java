package com.roadpatrol.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.roadpatrol.entity.IssueStatusHistory;

@Repository
public interface IssueStatusHistoryRepository 
        extends JpaRepository<IssueStatusHistory, UUID> {

    List<IssueStatusHistory> findByIssueId(UUID issueId);
}