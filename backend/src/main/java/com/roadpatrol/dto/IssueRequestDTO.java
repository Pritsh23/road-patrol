package com.roadpatrol.dto;

import java.util.List;

import com.roadpatrol.entity.IssueCategory;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;


@Getter @Setter
public class IssueRequestDTO {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private IssueCategory category;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    private List<String> imageUrls; // S3 URLs (or temp upload refs)
}
