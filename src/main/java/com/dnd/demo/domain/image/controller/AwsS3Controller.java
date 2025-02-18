package com.dnd.demo.domain.image.controller;

import com.dnd.demo.domain.image.service.AwsS3Service;
import com.dnd.demo.domain.image.dto.response.PresignedUrlResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "이미지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class AwsS3Controller {

	private final AwsS3Service awsS3Service;

	@GetMapping("/presigned-url")
	public ResponseEntity<PresignedUrlResponse> generatePresignedUrl(
		@RequestParam("contentType") String contentType) {
		PresignedUrlResponse response = awsS3Service.generatePresignedUrl(contentType);
		return ResponseEntity.ok(response);
	}
}
