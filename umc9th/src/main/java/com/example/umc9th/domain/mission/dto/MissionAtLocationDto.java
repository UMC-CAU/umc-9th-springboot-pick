package com.example.umc9th.domain.mission.dto;

import java.time.LocalDate;

public record MissionAtLocationDto(
        String storeName,
        String todo,
        Integer score,
        LocalDate deadline
) { }
