package com.example.umc9th.domain.review.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class MyReviewDto{
    private final Long reviewId;
    private final String storeName;
    private final String content;
    private final BigDecimal starScore;
    private final LocalDateTime createdAt;
    private final String replyComment;
}