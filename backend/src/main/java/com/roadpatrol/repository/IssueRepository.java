package com.roadpatrol.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.roadpatrol.entity.Issue;
import com.roadpatrol.entity.IssueCategory;
import com.roadpatrol.entity.IssueStatus;

@Repository
public interface IssueRepository extends JpaRepository<Issue, UUID> {

    List<Issue> findByStatus(IssueStatus status);

    List<Issue> findByCategory(IssueCategory category);

    @Query("SELECT i FROM Issue i WHERE i.user.id = :userId")
    List<Issue> findByUserId(UUID userId);

    @Query("""
    SELECT i FROM Issue i
    WHERE 
    (6371 * acos(
        cos(radians(:lat)) * cos(radians(i.latitude)) *
        cos(radians(i.longitude) - radians(:lng)) +
        sin(radians(:lat)) * sin(radians(i.latitude))
    )) < :radius
""")
List<Issue> findNearbyIssues(
        @Param("lat") double lat,
        @Param("lng") double lng,
        @Param("radius") double radius
);
}