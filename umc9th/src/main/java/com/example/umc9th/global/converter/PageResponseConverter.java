package com.example.umc9th.global.converter;

import com.example.umc9th.global.dto.PageResponse;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

public class PageResponseConverter {

    public static <T> PageResponse<T> from(Page<T> page) {
        return PageResponse.<T>builder()
                // ⭐ Stream 사용 (for문 금지 조건 충족)
                .content(page.getContent().stream().collect(Collectors.toList()))
                // 클라이언트에는 1-based 로 보여주기
                .page(page.getNumber() + 1)
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}