package com.roadpatrol.controller;

import org.springframework.web.bind.annotation.*;

import com.roadpatrol.dto.VoteRequestDTO;
import com.roadpatrol.dto.VoteResponseDTO;
import com.roadpatrol.service.VoteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/votes")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public VoteResponseDTO vote(@RequestBody VoteRequestDTO dto) {
        return voteService.vote(dto);
    }
}
