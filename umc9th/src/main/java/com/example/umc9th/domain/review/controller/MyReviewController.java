package com.example.umc9th.domain.review.controller;

import com.example.umc9th.domain.review.dto.MyReviewDto;
import com.example.umc9th.domain.review.service.MyReviewService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.apiPayload.code.GeneralSuccessCode;
import com.example.umc9th.global.converter.PageResponseConverter;
import com.example.umc9th.global.dto.PageResponse;
import com.example.umc9th.global.web.page.OneBasedPageable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/my/reviews")
public class MyReviewController {

    private final MyReviewService service;

    @GetMapping
    @Operation(
            summary = "내가 작성한 리뷰 목록 조회",
            description = """
                    - 로그인한 회원의 리뷰 목록을 페이지네이션하여 조회합니다.
                    - page는 1 이상의 정수입니다. (쿼리 스트링 ?page=1)
                    - 한 페이지에 10개씩 고정으로 조회합니다.
                    """
    )
    public ApiResponse<PageResponse<MyReviewDto>> getMyReviews(
            @RequestHeader("X-MEMBER-ID")
            @Parameter(description = "회원 ID, 실제 운영에서는 @AuthenticationPrincipal 사용 권장")
            Long memberId,

            @RequestParam(required = false)
            @Parameter(description = "가게 이름 부분 검색(옵션)")
            String storeName,

            @RequestParam(required = false)
            @Parameter(description = "별점대 필터 (5,4,3,2,1 중 하나, 옵션)")
            Integer rating,

            @OneBasedPageable
            @Parameter(description = "1 이상의 페이지 번호. 쿼리 스트링 ?page=1 형태로 전달")
            Pageable pageable
    ) {
        var page = service.getMyReviews(memberId, storeName, rating, pageable);
        PageResponse<MyReviewDto> body = PageResponseConverter.from(page);
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, body);
    }
}