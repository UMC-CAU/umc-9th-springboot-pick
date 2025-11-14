package com.example.umc9th.domain.review.controller;

import com.example.umc9th.domain.review.dto.MyReviewDto;
import com.example.umc9th.domain.review.service.MyReviewService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/my/reviews")
public class MyReviewController {

    private final MyReviewService service;

    @GetMapping
    public ApiResponse<List<MyReviewDto>> getMyReviews(
            // 실제 운영에선 @AuthenticationPrincipal 로부터 memberId 추출 권장
            @RequestHeader("X-MEMBER-ID") Long memberId,
            @RequestParam(required = false) String storeName, // 부분 검색
            @RequestParam(required = false) Integer rating,   // 별점대 (5/4/3/2/1)
            @RequestParam(required = false) Integer limit       // 기본 15
    ) {
        List<MyReviewDto> result = service.getMyReviews(memberId, storeName, rating, limit);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, result);
    }
}