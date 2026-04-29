package com.roadpatrol.dto;
import com.roadpatrol.entity.IssueStatus;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;
@Getter @Setter
public class IssueUpdateDTO {

    @NotNull
    private IssueStatus status;
}