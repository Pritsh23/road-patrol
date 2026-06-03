package com.roadpatrol.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roadpatrol.entity.Issue;
import com.roadpatrol.entity.IssueImage;

@Repository
public interface IssueImageRepository
        extends JpaRepository<IssueImage, UUID> {

    List<IssueImage> findByIssue(Issue issue);
}
