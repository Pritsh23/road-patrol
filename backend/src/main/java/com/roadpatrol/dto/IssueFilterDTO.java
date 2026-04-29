package com.roadpatrol.dto;

import com.roadpatrol.entity.IssueCategory;
import com.roadpatrol.entity.IssueStatus;
import lombok.*;

@Getter @Setter
public class IssueFilterDTO {

    private IssueCategory category;
    private IssueStatus status;

    private Double lat;
    private Double lng;
    private Double radiusKm;

    private String sortBy; // priority / latest
}
