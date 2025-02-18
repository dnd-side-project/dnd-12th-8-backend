package com.dnd.demo.domain.image.service;

import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.dnd.demo.domain.image.dto.response.PresignedUrlResponse;
import com.dnd.demo.global.exception.CustomException;
import com.dnd.demo.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

	private final AmazonS3 amazonS3;

	@Value("${aws.s3.bucket}")
	private String bucketName;

	private static final Map<String, String> CONTENT_TYPE_TO_EXTENSION = Map.ofEntries(
		Map.entry("image/jpeg", "jpeg"),
		Map.entry("image/jpg", "jpg"),
		Map.entry("image/png", "png"),
		Map.entry("image/webp", "webp"),
		Map.entry("image/gif", "gif"),
		Map.entry("image/svg+xml", "svg"),
		Map.entry("image/bmp", "bmp"),
		Map.entry("image/tiff", "tiff"),
		Map.entry("image/heif", "heif"),
		Map.entry("image/heic", "heic"),
		Map.entry("image/x-icon", "ico")
	);

	public PresignedUrlResponse generatePresignedUrl(String contentType) {
		String fileExtension = CONTENT_TYPE_TO_EXTENSION.get(contentType);
		if (fileExtension == null) {
			throw new CustomException(ErrorCode.INVALID_IMAGE_TYPE);
		}

		String fileName = UUID.randomUUID().toString() + "." + fileExtension;
		String objectKey = "post-image/" + fileName;

		Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 10); // 10분 후 만료
		GeneratePresignedUrlRequest generatePresignedUrlRequest =
			new GeneratePresignedUrlRequest(bucketName, objectKey)
				.withMethod(HttpMethod.PUT)
				.withExpiration(expiration);

		generatePresignedUrlRequest.addRequestParameter("Content-Type", contentType);

		URL presignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

		return new PresignedUrlResponse(
			presignedUrl.toString(),
			"https://" + bucketName + ".s3.amazonaws.com/" + objectKey
		);
	}
}
