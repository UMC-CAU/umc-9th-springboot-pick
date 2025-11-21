package com.example.umc9th.domain.mission.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMissionChallengeResponse {

    private Long userMissionId;
    private Long missionId;
    private Long memberId;
    private boolean finished;
}

