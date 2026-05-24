package com.roadpatrol.service;

import com.roadpatrol.dto.AIResponseDTO;
import com.roadpatrol.dto.IssueRequestDTO;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AIServiceImpl implements AIService {

    @Override
    public AIResponseDTO analyzeIssue(
            IssueRequestDTO dto
    ) {

        Random random = new Random();

        float severity =
                1 + random.nextFloat() * 9;

        boolean spam =
                dto.getDescription()
                        .toLowerCase()
                        .contains("test");

        return AIResponseDTO.builder()
                .detectedCategory(dto.getCategory().name())
                .severityScore(severity)
                .spam(spam)
                .confidenceScore(85f)
                .spamReason(
                        spam ? "Suspicious description" : null
                )
                .build();
    }
}