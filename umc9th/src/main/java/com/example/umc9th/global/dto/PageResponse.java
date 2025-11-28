package com.example.umc9th.global.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PageResponse<T> {

    private final List<T> content;
    private final int page;          // 1-based 페이지 번호
    private final int size;          // 페이지 크기
    private final long totalElements;
    private final int totalPages;
    private final boolean last;
}