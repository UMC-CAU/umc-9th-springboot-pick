package com.example.umc9th.domain.review.query;

import com.example.umc9th.domain.review.dto.MyReviewDto;
import com.example.umc9th.domain.review.entity.QReply;
import com.example.umc9th.domain.review.entity.QReview;
import com.example.umc9th.domain.store.entity.QStore;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewQuery {

    private final JPAQueryFactory queryFactory;

    public Page<MyReviewDto> searchReview(
            Long memberId,
            String storeName,
            Integer ratingBand,
            Pageable pageable
    ) {

        // Q클래스 정의
        QReview review = QReview.review;
        QStore store   = QStore.store;
        QReply reply = QReply.reply;

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
        // content 쿼리 (실제 데이터)
        JPAQuery<MyReviewDto> contentQuery = queryFactory
                .select(Projections.constructor(
                        MyReviewDto.class,
                        review.id,
                        review.store.name,
                        review.content,
                        review.star_score,
                        review.createdAt,
                        reply.comment
                ))
                .from(review)
                .join(review.store, store)
                .leftJoin(review.reply, reply)
                .where(builder)
                .orderBy(review.createdAt.desc(), review.id.desc());

        // total count 쿼리
        Long total = queryFactory
                .select(review.count())
                .from(review)
                .join(review.store, store)
                .leftJoin(review.reply, reply)
                .where(builder)
                .fetchOne();

        long totalCount = (total != null) ? total : 0L;

        // 페이징 적용
        List<MyReviewDto> content = contentQuery
                .offset(pageable.getOffset())          // (page-1)*size
                .limit(pageable.getPageSize())         // size (여기선 10)
                .fetch();

        return new PageImpl<>(content, pageable, totalCount);
    }
}