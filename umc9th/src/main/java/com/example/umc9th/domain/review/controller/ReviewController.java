package com.example.umc9th.domain.review.controller;


import com.example.umc9th.domain.review.constant.ReviewSuccessCode;
import com.example.umc9th.domain.review.dto.ReviewCreateRequest;
import com.example.umc9th.domain.review.dto.ReviewCreateResponse;
import com.example.umc9th.domain.review.service.ReviewService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/store")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 가게에 리뷰 추가하기
     * POST /api/v1/store/{storeId}/reviews
     */
    @PostMapping("/{storeId}/reviews")
    public ApiResponse<ReviewCreateResponse> createReview(
            @PathVariable Long storeId,
            @RequestBody @Valid ReviewCreateRequest request
    ) {
        ReviewCreateResponse response = reviewService.createReview(storeId, request);
        return ApiResponse.onSuccess(ReviewSuccessCode.REVIEW_CREATE_SUCCESS, response);
    }
}