package com.example.umc9th.domain.review.service;

import com.example.umc9th.domain.review.dto.MyReviewDto;
import com.example.umc9th.domain.review.query.ReviewQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyReviewService {

    private final ReviewQuery reviewQuery;

    public Page<MyReviewDto> getMyReviews(
            Long memberId,
            String storeName,
            Integer ratingBand,
            Pageable pageable
    ) {
        // limit 계산은 이제 OneBasedPageableResolver에서 size=10으로 고정
        return reviewQuery.searchReview(memberId, storeName, ratingBand, pageable);
    }
}