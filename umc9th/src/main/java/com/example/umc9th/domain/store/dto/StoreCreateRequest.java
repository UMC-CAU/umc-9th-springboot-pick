package com.example.umc9th.domain.store.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreCreateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String ownerNumber;

    @NotBlank
    private String address;
}