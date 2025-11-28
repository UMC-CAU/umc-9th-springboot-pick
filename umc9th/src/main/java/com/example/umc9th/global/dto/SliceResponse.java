package com.example.umc9th.global.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SliceResponse<T> {

    private final List<T> content;
    private final int page;      // 1-based
    private final int size;
    private final boolean hasNext;
}