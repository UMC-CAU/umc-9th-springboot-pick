package com.example.umc9th.domain.mission.converter;


import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.mission.dto.UserMissionChallengeResponse;
import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.mission.entity.mapping.UserMission;

public class UserMissionConverter {

    // Member + Mission -> UserMission Entity
    public static UserMission toEntity(
            Member member,
            Mission mission
    ) {
        return UserMission.builder()
                .member(member)
                .mission(mission)
                .isFinished(false)
                .build();
    }

    // Entity -> DTO
    public static UserMissionChallengeResponse toChallengeResponse(
            UserMission userMission
    ) {
        return UserMissionChallengeResponse.builder()
                .userMissionId(userMission.getId())
                .missionId(userMission.getMission().getId())
                .memberId(userMission.getMember().getId())
                .finished(userMission.isFinished())
                .build();
    }
}