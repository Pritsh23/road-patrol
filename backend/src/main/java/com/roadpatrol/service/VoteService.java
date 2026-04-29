package com.roadpatrol.service;
import com.roadpatrol.dto.VoteRequestDTO;
import com.roadpatrol.dto.VoteResponseDTO;

public interface VoteService {
    VoteResponseDTO vote(VoteRequestDTO dto);
}