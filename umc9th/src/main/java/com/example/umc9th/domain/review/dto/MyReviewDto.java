package com.example.umc9th.domain.review.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MyReviewDto(
        Long reviewId,
        String storeName,
        String content,
        BigDecimal starScore,
        LocalDateTime createdAt,
        String replyComment
) {}