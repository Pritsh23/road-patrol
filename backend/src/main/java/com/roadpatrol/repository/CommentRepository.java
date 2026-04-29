package com.roadpatrol.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.roadpatrol.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    List<Comment> findByIssueIdOrderByCreatedAtDesc(UUID issueId);
}
