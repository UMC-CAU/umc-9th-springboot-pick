package com.example.umc9th.domain.review.converter;

import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.review.dto.ReviewCreateRequest;
import com.example.umc9th.domain.review.dto.ReviewCreateResponse;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.store.entity.Store;

public class ReviewConverter {

    // DTO -> Entity
    public static Review toEntity(
            ReviewCreateRequest dto,
            Member member,
            Store store
    ) {
        return Review.builder()
                .content(dto.getContent())
                .star_score(dto.getStarScore())
                .member(member)
                .store(store)
                .build();
    }

    // Entity -> DTO
    public static ReviewCreateResponse toCreateResponse(
            Review review
    ) {
        return ReviewCreateResponse.builder()
                .reviewId(review.getId())
                .storeId(review.getStore().getId())
                .memberId(review.getMember().getId())
                .content(review.getContent())
                .starScore(review.getStar_score())
                .createdAt(review.getCreatedAt())
                .build();
    }
}