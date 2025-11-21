package com.example.umc9th.domain.mission.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMissionChallengeRequest {

    @NotNull
    private Long memberId;   // 나중에 JWT에서 꺼내면 제거 가능
}