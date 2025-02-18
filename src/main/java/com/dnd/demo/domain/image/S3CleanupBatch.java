package com.dnd.demo.domain.image;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.dnd.demo.domain.project.repository.ProjectDetailRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// TODO: S3에 쌓이는 사용되지않는 이미지 배치처리하기 -> 후순위

// @Slf4j
// @Component
// @RequiredArgsConstructor
// public class S3CleanupBatch {
//
// 	private final AmazonS3 amazonS3;
// 	private final ProjectDetailRepository projectDetailRepository;
//
// 	@Value("${aws.s3.bucket}")
// 	private String bucketName;
//
// 	@Scheduled(cron = "0 0 3 * * ?") // 매일 새벽 3시에 실행
// 	public void cleanupUnusedS3Images() {
// 		log.info("🔍 S3 불필요한 이미지 정리 배치 시작...");
//
// 		// ✅ 1️⃣ DB에 저장된 S3 이미지 URL 목록 가져오기
// 		List<String> dbImages = projectDetailRepository.findAllImageUrls(); // DB에서 이미지 URL 조회
// 		Set<String> dbImageSet = new HashSet<>(dbImages); // 빠른 비교를 위해 Set 변환
//
// 		// ✅ 2️⃣ S3의 모든 객체(파일) 리스트 가져오기
// 		ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(bucketName);
// 		ListObjectsV2Result result = amazonS3.listObjectsV2(request);
//
// 		List<String> s3Images = result.getObjectSummaries().stream()
// 			.map(S3ObjectSummary::getKey)
// 			.collect(Collectors.toList());
//
// 		int deleteCount = 0;
//
// 		// ✅ 3️⃣ S3에 존재하지만 DB에는 없는 이미지 삭제
// 		for (String s3ImageKey : s3Images) {
// 			String s3ImageUrl = "https://" + bucketName + ".s3.amazonaws.com/" + s3ImageKey;
//
// 			if (!dbImageSet.contains(s3ImageUrl)) {
// 				log.info("🗑 삭제할 이미지 발견: {}", s3ImageUrl);
// 				amazonS3.deleteObject(bucketName, s3ImageKey);
// 				deleteCount++;
// 			}
// 		}
//
// 		log.info("✅ S3 불필요한 이미지 정리 완료. 삭제된 이미지 개수: {}", deleteCount);
// 	}
// }
