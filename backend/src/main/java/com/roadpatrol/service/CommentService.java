package com.roadpatrol.service;

import org.springframework.stereotype.Service;

import com.roadpatrol.dto.CommentRequestDTO;
import com.roadpatrol.dto.CommentResponseDTO;
import com.roadpatrol.entity.Comment;
import com.roadpatrol.entity.Issue;
import com.roadpatrol.entity.User;
import com.roadpatrol.repository.CommentRepository;
import com.roadpatrol.repository.IssueRepository;
import com.roadpatrol.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    public CommentResponseDTO addComment(CommentRequestDTO dto) {

        User user = getCurrentUser();
        Issue issue = issueRepository.findById(dto.getIssueId())
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        Comment comment = Comment.builder()
                .user(user)
                .issue(issue)
                .content(dto.getContent())
                .build();

        commentRepository.save(comment);

        return mapToDTO(comment);
    }

    private CommentResponseDTO mapToDTO(Comment comment) {
        return CommentResponseDTO.builder()
                .id(comment.getId())
                .userId(comment.getUser().getId())
                .userName(comment.getUser().getName())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        // return userRepository.findByEmail(email)
        //         .orElseThrow(() -> new RuntimeException("User not found"));
            return userRepository.findByEmail("test@gmail.com")
            .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
