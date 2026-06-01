package com.roadpatrol.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.roadpatrol.entity.Issue;
import com.roadpatrol.entity.User;
import com.roadpatrol.entity.Vote;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
@Repository
public interface VoteRepository extends JpaRepository<Vote, UUID> {

    Optional<Vote> findByUserAndIssue(User user, Issue issue);
   @Modifying
@Transactional
void deleteByUserAndIssue(User user, Issue issue);

    @Query("SELECT COUNT(v) FROM Vote v WHERE v.issue.id = :issueId AND v.voteType = 'UPVOTE'")
    int countUpvotes(UUID issueId);

    @Query("SELECT COUNT(v) FROM Vote v WHERE v.issue.id = :issueId AND v.voteType = 'DOWNVOTE'")
    int countDownvotes(UUID issueId);

    
}
