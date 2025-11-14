package com.example.umc9th.domain.store.dto;

public record StoreListItemDto(
        Long storeId,
        String name,
        String address,
        String region
) { }