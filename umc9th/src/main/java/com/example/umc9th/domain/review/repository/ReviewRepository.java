package com.example.umc9th.domain.review.repository;

import com.example.umc9th.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
        INSERT INTO review (content, star_score, created_at, member_id, store_id)"
        VALUES (:content, :starScore, NOW(), :memberId, :storeId)
    """, nativeQuery = true)
    int insertNative(@Param("content") String content,
                     @Param("starScore") String starScore, // DECIMAL(2,1) → "4.5" 형태 문자열 or BigDecimal.toPlainString()
                     @Param("memberId") Long memberId,
                     @Param("storeId") Long storeId);

}
