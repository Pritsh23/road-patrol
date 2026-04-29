package com.roadpatrol.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;


@Getter @Setter @Builder
public class VoteResponseDTO {

    private UUID issueId;
    private int totalVotes;
}