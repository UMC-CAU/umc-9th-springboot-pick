package com.example.umc9th.domain.review.controller;


import com.example.umc9th.domain.review.constant.ReviewSuccessCode;
import com.example.umc9th.domain.review.dto.ReviewCreateRequest;
import com.example.umc9th.domain.review.dto.ReviewCreateResponse;
import com.example.umc9th.domain.review.service.ReviewService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/store")
public class ReviewController {

    private final ReviewService reviewService;


     // 가게에 리뷰 추가하기
     // POST /api/v1/store/{storeId}/reviews
    @PostMapping("/{storeId}/reviews")
    @Operation(
            summary = "가게에 리뷰 추가하기"
    )
    public ApiResponse<ReviewCreateResponse> createReview(
            @PathVariable
            @Parameter(description = "특정 가게 id(Path Variable)")
            Long storeId,

            @RequestBody
            @Valid
            @Parameter(description = "리뷰 내용 + 별점 (0.0~5.0) + memberId")
            ReviewCreateRequest request
    ) {
        ReviewCreateResponse response = reviewService.createReview(storeId, request);
        return ApiResponse.onSuccess(ReviewSuccessCode.REVIEW_CREATE_SUCCESS, response);
    }
}