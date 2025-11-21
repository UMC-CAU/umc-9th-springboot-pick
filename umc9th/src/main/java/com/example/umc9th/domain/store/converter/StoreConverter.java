package com.example.umc9th.domain.store.converter;

import com.example.umc9th.domain.store.dto.StoreCreateRequest;
import com.example.umc9th.domain.store.dto.StoreCreateResponse;
import com.example.umc9th.domain.store.entity.Location;
import com.example.umc9th.domain.store.entity.Store;

public class StoreConverter {
     // DTO -> Entity
    public static Store toEntity(
            StoreCreateRequest dto,
            Location location
    ) {
        return Store.builder()
                .name(dto.getName())
                .ownerNumber(dto.getOwnerNumber())
                .address(dto.getAddress())
                .location(location)
                .build();
    }

    // Entity -> DTO
    public static StoreCreateResponse toCreateResponse(
            Store store
    ) {
        return StoreCreateResponse.builder()
                .storeId(store.getId())
                .name(store.getName())
                .ownerNumber(store.getOwnerNumber())
                .address(store.getAddress())
                .locationId(store.getLocation().getId())
                .locationName(store.getLocation().getName())   // "...구" 단위
                .build();
    }
}