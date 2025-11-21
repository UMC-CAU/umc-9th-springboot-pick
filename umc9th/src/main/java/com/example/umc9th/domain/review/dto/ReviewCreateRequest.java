package com.example.umc9th.domain.review.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewCreateRequest {

    @NotBlank
    private String content;

    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    private BigDecimal starScore;

    @NotNull
    private Long memberId;   // 나중에 JWT에서 가져오면 제거
}