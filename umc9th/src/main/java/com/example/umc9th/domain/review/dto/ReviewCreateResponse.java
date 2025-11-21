package com.example.umc9th.domain.review.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewCreateResponse {

    private Long reviewId;
    private Long storeId;
    private Long memberId;
    private String content;
    private BigDecimal starScore;
    private LocalDateTime createdAt;
}
