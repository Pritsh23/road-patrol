package com.roadpatrol.service;
import com.roadpatrol.dto.AIResponseDTO;
import com.roadpatrol.dto.IssueRequestDTO;

public interface AIService {
    AIResponseDTO analyzeIssue(IssueRequestDTO dto);
}
