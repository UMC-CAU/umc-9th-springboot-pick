package com.example.umc9th.domain.review.service;


import com.example.umc9th.domain.review.dto.MyReviewDto;
import com.example.umc9th.domain.review.query.ReviewQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyReviewService {

    private final ReviewQuery reviewQuery;

    public List<MyReviewDto> getMyReviews(Long memberId, String storeName, Integer ratingBand, Integer limit) {
        int size = (limit == null ? 15 : limit);
        return reviewQuery.searchReview(memberId, storeName, ratingBand, size);
    }
}