package com.roadpatrol.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.roadpatrol.entity.IssueCluster;

@Repository
public interface IssueClusterRepository extends JpaRepository<IssueCluster, UUID> {

    @Query("""
        SELECT c FROM IssueCluster c
        WHERE 
        (6371 * acos(
            cos(radians(:lat)) * cos(radians(c.centerLat)) *
            cos(radians(c.centerLng) - radians(:lng)) +
            sin(radians(:lat)) * sin(radians(c.centerLat))
        )) < :radius
    """)
    List<IssueCluster> findNearbyClusters(
            double lat,
            double lng,
            double radius
    );
}