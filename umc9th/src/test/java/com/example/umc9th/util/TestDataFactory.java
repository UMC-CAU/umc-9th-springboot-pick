package com.example.umc9th.util;

import com.example.umc9th.domain.member.entity.Category;
import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.member.entity.Term;
import com.example.umc9th.domain.member.entity.mapping.MemberFood;
import com.example.umc9th.domain.member.entity.mapping.MemberTerm;
import com.example.umc9th.domain.member.enums.Food;
import com.example.umc9th.domain.member.enums.Gender;
import com.example.umc9th.domain.member.enums.SocialName;
import com.example.umc9th.domain.member.enums.TermName;
import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.mission.entity.mapping.UserMission;
import com.example.umc9th.domain.review.entity.Picture;
import com.example.umc9th.domain.review.entity.Reply;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.store.entity.Location;
import com.example.umc9th.domain.store.entity.Store;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestDataFactory {

    public static Member createMember(int index) {
        return Member.builder()
                .name("member" + index)                        // nullable = false
                .gender(Gender.MALE)                           // nullable = false
                .address("Seoul City")                         // nullable = false
                .socialName(SocialName.Kakao)                  // nullable = false
                .email("member" + index + "@test.com")         // nullable = false
                .point(0)                                      // nullable = false
                .birth(LocalDate.of(2025, 10, 10))               // nullable = true
                .phoneNum("010-0000-" + String.format("%04d", index)) // nullable = true
                .nickname("user" + index)                      // nullable = true
                .profileUrl("http://member" + index + "profile.com")
                .build();
    }

    public static Category createCategory(Food categoryType) {
        return Category.builder()
                .category(categoryType) // nullable = false
                .build();
    }

    public static Term createTerm(TermName termName) {
        return Term.builder()
                .termName(termName) // nullable = false
                .build();
    }

    public static MemberFood createMemberFood(Member member, Category category) {
        return MemberFood.builder()
                .member(member)
                .category(category)
                .build();
    }

    public static MemberTerm createMemberTerm(Member member, Term term) {
        return MemberTerm.builder()
                .member(member)
                .term(term)
                .build();
    }

    public static Mission createMission(Store store, int index) {
        return Mission.builder()
                .deadline(LocalDate.now().plusDays(7))
                .todo("Mission " + index)
                .score(10)
                .store(store)
                .build();
    }

    public static UserMission createUserMission(Member member, Mission mission, boolean isFinished) {
        return UserMission.builder()
                .member(member)
                .mission(mission)
                .isFinished(isFinished)
                .build();
    }

    public static Review createReview(Member member, Store store, int index) {
        return Review.builder()
                .content("review content " + index)
                .star_score(BigDecimal.valueOf((index % 5) + 0.5))
                .member(member)
                .store(store)
                .build();
    }

    public static Reply createReply(Review review, int index) {
        return Reply.builder()
                .comment("reply comment " + index)
                .review(review)
                .build();
    }

    public static Picture createPicture(Review review, int index) {
        return Picture.builder()
                .url("https://test.image/" + index)
                .review(review)
                .build();
    }

    public static Location createLocation(String name) {
        return Location.builder()
                .name(name)
                .build();
    }

    public static Store createStore(Location location, int index) {
        return Store.builder()
                .name("Store " + index)
                .ownerNumber("OWN-" + String.format("%04d", index))
                .address("Seoul Street " + index)
                .location(location)
                .build();
    }

}
