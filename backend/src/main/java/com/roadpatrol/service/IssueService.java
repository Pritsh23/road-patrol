package com.roadpatrol.service;
import java.util.List;
import java.util.UUID;
import com.roadpatrol.dto.IssueRequestDTO;
import com.roadpatrol.dto.IssueResponseDTO;
import com.roadpatrol.entity.IssueStatus;


public interface IssueService {

    IssueResponseDTO createIssue(IssueRequestDTO dto);

    IssueResponseDTO getIssueById(UUID id);

    List<IssueResponseDTO> getNearbyIssues(double lat, double lng, double radius);

    IssueResponseDTO updateIssueStatus(UUID issueId, IssueStatus status);
}
