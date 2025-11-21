package com.example.umc9th.domain.mission.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MissionCreateRequest {

    @NotNull
    @FutureOrPresent
    private LocalDate deadline;

    @NotBlank
    private String todo;

    @NotNull
    @Min(1)
    private Integer score;
}
