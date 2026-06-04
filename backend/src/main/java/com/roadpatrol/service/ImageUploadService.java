package com.roadpatrol.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.roadpatrol.dto.UploadResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageUploadService {

    private final Cloudinary cloudinary;

    public UploadResponseDTO upload(MultipartFile file) {

        try {

            Map<?, ?> result =
                    cloudinary.uploader().upload(
                            file.getBytes(),
                            ObjectUtils.emptyMap()
                    );

            return UploadResponseDTO.builder()
                    .imageUrl(
                            result.get("secure_url").toString()
                    )
                    .publicId(
                            result.get("public_id").toString()
                    )
                    .build();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Image upload failed",
                    e
            );
        }
    }
}