package com.example.umc9th.domain.mission.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class MissionSummaryDto {

    private final Long missionId;
    private final String todo;
    private final Integer score;
    private final LocalDate deadline;
}