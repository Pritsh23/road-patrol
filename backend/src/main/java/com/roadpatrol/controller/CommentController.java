package com.roadpatrol.controller;

import org.springframework.web.bind.annotation.*;

import com.roadpatrol.dto.CommentRequestDTO;
import com.roadpatrol.dto.CommentResponseDTO;
import com.roadpatrol.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommentResponseDTO addComment(@RequestBody CommentRequestDTO dto) {
        return commentService.addComment(dto);
    }
}