package com.example.umc9th.domain.store.controller;

import com.example.umc9th.domain.store.constant.StoreSuccessCode;
import com.example.umc9th.domain.store.dto.StoreCreateRequest;
import com.example.umc9th.domain.store.dto.StoreCreateResponse;
import com.example.umc9th.domain.store.service.StoreService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/store")
public class StoreController {

    private final StoreService storeService;

    // 특정 지역에 가게 추가
    // Post /api/v1/store/locations/{locationId}
    @PostMapping("/locations/{locationId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<StoreCreateResponse> createStore(
            @PathVariable Long locationId,
            @RequestBody @Valid StoreCreateRequest request
    ) {
        StoreCreateResponse response = storeService.createStore(locationId, request);
        return ApiResponse.onSuccess(StoreSuccessCode.STORE_CREATE_SUCCESS, response);
    }
}