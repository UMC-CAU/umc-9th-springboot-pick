package com.example.umc9th.domain.store.controller;

import com.example.umc9th.domain.store.dto.StoreListItemDto;
import com.example.umc9th.domain.store.service.StoreSearchService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores/search")
public class StoreSearchController {

    private final StoreSearchService service;

    // 기본: page + size
    @GetMapping
    public ApiResponse<Page<StoreListItemDto>> search(
            @RequestParam(required = false) String regions, // 예: ?regions=강남구&regions=영등포구
            @RequestParam(required = false, name = "q") String keyword,
            @RequestParam(defaultValue = "latest") String sort,   // latest | name
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size
    ) {
        Page<StoreListItemDto> result = service.searchStores(regions, keyword, sort, page, size);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, result);
    }

    // 커서 기반
    @GetMapping("/cursor")
    public ApiResponse<List<StoreListItemDto>> searchByCursor(
            @RequestParam(required = false) String regions,
            @RequestParam(required = false, name = "q") String keyword,
            @RequestParam(defaultValue = "latest") String sort,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "15") int size
    ) {
        List<StoreListItemDto> result = service.searchStoresByCursor(regions, keyword, sort, cursorId, size);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, result);
    }
}