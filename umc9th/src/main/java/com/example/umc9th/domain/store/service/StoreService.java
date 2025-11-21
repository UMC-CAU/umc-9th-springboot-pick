package com.example.umc9th.domain.store.service;

import com.example.umc9th.domain.store.converter.StoreConverter;
import com.example.umc9th.domain.store.dto.StoreCreateRequest;
import com.example.umc9th.domain.store.dto.StoreCreateResponse;
import com.example.umc9th.domain.store.entity.Location;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.domain.store.repository.LocationRepository;
import com.example.umc9th.domain.store.repository.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final LocationRepository locationRepository;

    public StoreCreateResponse createStore(Long locationId, StoreCreateRequest request) {

        // 1. Location 조회
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Location not found. id=" + locationId));

        // 2. Store 엔티티 생성
        Store store = StoreConverter.toEntity(request, location);

        // 3. 저장
        Store saved = storeRepository.save(store);

        // 4. Response 변환
        return StoreConverter.toCreateResponse(saved);
    }
}