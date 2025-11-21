package com.example.umc9th.domain.mission.dto;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MissionCreateResponse {

    private Long missionId;
    private Long storeId;
    private String storeName;

    private LocalDate deadline;
    private String todo;
    private Integer score;

    private LocalDateTime createdAt;
}