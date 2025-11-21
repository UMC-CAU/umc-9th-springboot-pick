package com.example.umc9th.domain.review.service;

import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.member.repository.MemberRepository;
import com.example.umc9th.domain.review.converter.ReviewConverter;
import com.example.umc9th.domain.review.dto.ReviewCreateRequest;
import com.example.umc9th.domain.review.dto.ReviewCreateResponse;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.domain.store.repository.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;


    public ReviewCreateResponse createReview(Long storeId, ReviewCreateRequest request) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Store not found. id=" + storeId));

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Member not found. id=" + request.getMemberId()));

        Review review = ReviewConverter.toEntity(request, member, store);
        Review saved = reviewRepository.save(review);

        return ReviewConverter.toCreateResponse(saved);
    }
}