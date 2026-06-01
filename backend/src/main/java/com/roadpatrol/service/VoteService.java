package com.roadpatrol.service;
import java.util.UUID;

import com.roadpatrol.dto.VoteRequestDTO;
import com.roadpatrol.dto.VoteResponseDTO;

public interface VoteService {
    VoteResponseDTO vote(VoteRequestDTO dto);
    void removeVote(UUID issueId);

    VoteResponseDTO getVoteCount(UUID issueId);
}