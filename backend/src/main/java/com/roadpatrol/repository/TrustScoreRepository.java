package com.roadpatrol.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.roadpatrol.entity.TrustScore;
import com.roadpatrol.entity.User;

@Repository
public interface TrustScoreRepository extends JpaRepository<TrustScore, UUID> {

    Optional<TrustScore> findByUser(User user);
}
