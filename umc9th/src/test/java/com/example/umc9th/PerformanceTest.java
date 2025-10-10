package com.example.umc9th;

import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.mission.entity.mapping.UserMission;
import com.example.umc9th.domain.review.entity.Picture;
import com.example.umc9th.domain.review.entity.Reply;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.store.entity.Location;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.util.TestDataFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PerformanceTest {

    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    void setup() {
        Location location = TestDataFactory.createLocation("Seoul");
        em.persist(location);

        Store store = TestDataFactory.createStore(location, 1);
        em.persist(store);

        for (int i = 0; i < 50; i++) {
            Member member = TestDataFactory.createMember(i);
            em.persist(member);

            Mission mission = TestDataFactory.createMission(store, i);
            em.persist(mission);

            UserMission userMission = TestDataFactory.createUserMission(member, mission, false);
            em.persist(userMission);

            Review review = TestDataFactory.createReview(member, store, i);
            em.persist(review);

            Reply reply = TestDataFactory.createReply(review, i);
            em.persist(reply);

            Picture picture = TestDataFactory.createPicture(review, i);
            em.persist(picture);
        }

        em.flush();
        em.clear();
    }

    @Test
    void testReviewFetchPerformance() {
        long start = System.nanoTime();

        List<Member> members = em.createQuery(
                "select distinct m from Member m join fetch m.reviewList", Member.class
        ).getResultList();

        long end = System.nanoTime();
        System.out.println("조회 시간: " + (end - start) / 1_000_000 + "ms");
    }
}
