package com.roadpatrol.dto;
import lombok.*;
@Getter @Setter @Builder
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
}
