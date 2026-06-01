package com.roadpatrol.service;
import java.beans.Transient;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.roadpatrol.dto.VoteRequestDTO;
import com.roadpatrol.dto.VoteResponseDTO;
import com.roadpatrol.entity.Issue;
import com.roadpatrol.entity.User;
import com.roadpatrol.entity.Vote;
import com.roadpatrol.repository.IssueRepository;
import com.roadpatrol.repository.UserRepository;
import com.roadpatrol.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository; // ✅ ADD THIS

    @Override
    public VoteResponseDTO vote(VoteRequestDTO dto) {

        User user = getCurrentUser();

        Issue issue = issueRepository.findById(dto.getIssueId())
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        Optional<Vote> existing = voteRepository.findByUserAndIssue(user, issue);

        if (existing.isPresent()) {
            existing.get().setVoteType(dto.getVoteType());
            voteRepository.save(existing.get());
        } else {
            voteRepository.save(Vote.builder()
                    .user(user)
                    .issue(issue)
                    .voteType(dto.getVoteType())
                    .build());
        }

        int totalVotes = voteRepository.countUpvotes(issue.getId());

        return VoteResponseDTO.builder()
                .issueId(issue.getId())
                .totalVotes(totalVotes)
                .build();
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
      
    }
    @Override
public void removeVote(UUID issueId) {

    User user = getCurrentUser();

    Issue issue = issueRepository.findById(issueId)
            .orElseThrow(() ->
                    new RuntimeException("Issue not found"));

    voteRepository.deleteByUserAndIssue(
            user,
            issue
    );
}
@Override
@Transactional
public VoteResponseDTO getVoteCount(UUID issueId) {

    int totalVotes =
            voteRepository.countUpvotes(issueId);

    return VoteResponseDTO.builder()
            .issueId(issueId)
            .totalVotes(totalVotes)
            .build();
}
}
