package com.dnd.demo.domain.advertisement.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.demo.domain.advertisement.entity.Advertisement;
import com.dnd.demo.domain.advertisement.repository.AdvertisementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

	private final AdvertisementRepository advertisementRepository;

	@Transactional
	public void createIfAdvertised(Long projectId, boolean isAdvertised) {
		if (!isAdvertised || existsAdvertisementId(projectId)) {
			return;
		}

		Advertisement advertisement = Advertisement.create(
			projectId,
			LocalDate.now().toString(),
			LocalDate.now().plusDays(30).toString()
		);

		advertisementRepository.save(advertisement);
	}

	@Transactional(readOnly = true)
	public List<Advertisement> getAdvertisedProjects() {
		List<Advertisement> advertisements = advertisementRepository.findAll();
		return advertisements.isEmpty() ? Collections.emptyList() : advertisements;
	}

	@Transactional(readOnly = true)
	public boolean existsAdvertisementId(Long projectId) {
		return advertisementRepository.existsById(projectId);
	}
}
