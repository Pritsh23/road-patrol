package com.roadpatrol.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.roadpatrol.dto.UploadResponseDTO;
import com.roadpatrol.service.ImageUploadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/upload")
@RequiredArgsConstructor
public class UploadController {

        private final ImageUploadService imageUploadService;

        @PostMapping
        public UploadResponseDTO uploadImage(
                        @RequestParam("file") MultipartFile file) {

                return imageUploadService.upload(file);
        }
}
