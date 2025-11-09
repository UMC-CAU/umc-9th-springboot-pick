package com.example.umc9th.domain.review.query;

import com.example.umc9th.domain.review.dto.MyReviewDto;
import com.example.umc9th.domain.review.entity.QReview;
import com.example.umc9th.domain.store.entity.QStore;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQuery {

    private final JPAQueryFactory queryFactory;

    public List<MyReviewDto> searchReview(Long memberId, String storeName, Integer ratingBand, Integer limit) {

        // Q클래스 정의
        QReview review = QReview.review;
        QStore store   = QStore.store;

        // BooleanBuilder 정의
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(review.member.id.eq(memberId));

        // 동적 필터: 가게명
        if (storeName != null && !storeName.isBlank()) {
            builder.and(review.store.name.containsIgnoreCase(storeName.trim()));
        }

        // 동적 필터: 별점대
        if (ratingBand != null) {
            switch (ratingBand) {
                case 5 -> builder.and(review.star_score.eq(new BigDecimal("5.0")));
                case 4 -> builder.and(review.star_score.goe(new BigDecimal("4.0"))
                        .and(review.star_score.lt(new BigDecimal("5.0"))));
                case 3 -> builder.and(review.star_score.goe(new BigDecimal("3.0"))
                        .and(review.star_score.lt(new BigDecimal("4.0"))));
                case 2 -> builder.and(review.star_score.goe(new BigDecimal("2.0"))
                        .and(review.star_score.lt(new BigDecimal("3.0"))));
                case 1 -> builder.and(review.star_score.goe(new BigDecimal("1.0"))
                        .and(review.star_score.lt(new BigDecimal("2.0"))));
                default -> { /* 무시 */ }
            }
        }

        int size = (limit == null || limit <= 0) ? 15 : limit;

        // JPAQueryFactory + Q클래스 사용
        return queryFactory
                .select(Projections.constructor(
                        MyReviewDto.class,
                        review.id,
                        review.store.name,
                        review.content,
                        review.star_score,
                        review.createdAt
                ))
                .from(review)
                .join(review.store, store)
                .where(builder)                    // BooleanBuilder 사용
                .orderBy(review.createdAt.desc(), review.id.desc()) // 최신순
                .limit(size)
                .fetch();                          // 결과 반환
    }
}