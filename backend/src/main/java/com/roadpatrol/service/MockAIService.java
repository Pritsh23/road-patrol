package com.roadpatrol.service;

import org.springframework.stereotype.Service;
import com.roadpatrol.dto.AIResponseDTO;
import com.roadpatrol.dto.IssueRequestDTO;

@Service
public class MockAIService implements AIService {

    @Override
    public AIResponseDTO analyzeIssue(IssueRequestDTO dto) {

        // Mock logic
        return AIResponseDTO.builder()
                .detectedCategory(dto.getCategory())
                .severityScore((float) (Math.random() * 10))
                .isSpam(false)
                .isDuplicate(false)
                .build();
    }
}