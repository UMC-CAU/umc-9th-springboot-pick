package com.example.umc9th.domain.store.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreCreateResponse {

    private Long storeId;
    private String name;
    private String ownerNumber;
    private String address;

    // Location.name = "...구" 단위 (예: 동작구, 관악구 등)
    private Long locationId;
    private String locationName;
}