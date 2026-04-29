package com.roadpatrol.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import com.roadpatrol.dto.IssueRequestDTO;
import com.roadpatrol.dto.IssueResponseDTO;
import com.roadpatrol.entity.IssueStatus;
import com.roadpatrol.service.IssueService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/issues")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    // 🚀 Create Issue
    @PostMapping
    public IssueResponseDTO createIssue(@RequestBody IssueRequestDTO dto) {
        return issueService.createIssue(dto);
    }

    // 🔍 Get Issue by ID
    @GetMapping("/{id}")
    public IssueResponseDTO getIssue(@PathVariable UUID id) {
        return issueService.getIssueById(id);
    }

    // 📍 Nearby Issues
    @GetMapping("/nearby")
    public List<IssueResponseDTO> getNearbyIssues(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam double radius
    ) {
        return issueService.getNearbyIssues(lat, lng, radius);
    }

    // 🔄 Update Status
    @PatchMapping("/{id}/status")
    public IssueResponseDTO updateStatus(
            @PathVariable UUID id,
            @RequestParam IssueStatus status
    ) {
        return issueService.updateIssueStatus(id, status);
    }
}