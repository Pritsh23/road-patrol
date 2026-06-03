package com.roadpatrol.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.roadpatrol.service.ImageUploadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/upload")
@RequiredArgsConstructor
public class UploadController {

    private final ImageUploadService imageUploadService;

    @PostMapping
    public Map<String, String> uploadImage(
            @RequestParam("file") MultipartFile file
    ) {

        String imageUrl =
                imageUploadService.upload(file);

        return Map.of(
                "imageUrl",
                imageUrl
        );
    }
}
