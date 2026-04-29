package com.roadpatrol.dto;

import java.util.UUID;

import com.roadpatrol.entity.VoteType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VoteRequestDTO {

    @NotNull
    private UUID issueId;

    @NotNull
    private VoteType voteType;
}