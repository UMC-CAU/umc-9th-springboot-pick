package com.example.umc9th.domain.mission.converter;
import com.example.umc9th.domain.mission.dto.MissionCreateRequest;
import com.example.umc9th.domain.mission.dto.MissionCreateResponse;
import com.example.umc9th.domain.mission.dto.MissionSummaryDto;
import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.mission.entity.mapping.UserMission;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.global.dto.PageResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class MissionConverter {

    // DTO -> Entity
    public static Mission toEntity(
            MissionCreateRequest dto,
            Store store
    ) {
        return Mission.builder()
                .deadline(dto.getDeadline())
                .todo(dto.getTodo())
                .score(dto.getScore())
                .store(store)
                .build();
    }

    // Entity -> DTO
    public static MissionCreateResponse toCreateResponse(
            Mission mission
    ) {
        return MissionCreateResponse.builder()
                .missionId(mission.getId())
                .storeId(mission.getStore().getId())
                .storeName(mission.getStore().getName())
                .deadline(mission.getDeadline())
                .todo(mission.getTodo())
                .score(mission.getScore())
                .createdAt(mission.getCreatedAt())
                .build();
    }

    // 특정 미션 하나를 Summary DTO로 변환
    public static MissionSummaryDto toMissionSummaryDto(Mission mission) {
        return MissionSummaryDto.builder()
                .missionId(mission.getId())
                .todo(mission.getTodo())
                .score(mission.getScore())
                .deadline(mission.getDeadline())
                .build();
    }

    // Page<Mission> -> PageResponse<MissionSummaryDto>
    public static PageResponse<MissionSummaryDto> toMissionSummaryPage(Page<Mission> missionPage) {
        List<MissionSummaryDto> content = missionPage.getContent().stream()
                .map(MissionConverter::toMissionSummaryDto)
                .collect(Collectors.toList());

        return PageResponse.<MissionSummaryDto>builder()
                .content(content)
                .page(missionPage.getNumber() + 1)
                .size(missionPage.getSize())
                .totalElements(missionPage.getTotalElements())
                .totalPages(missionPage.getTotalPages())
                .last(missionPage.isLast())
                .build();
    }

    // UserMission 하나를 MissionSummaryDto로 변환
    public static MissionSummaryDto toMissionSummaryDto(UserMission userMission) {
        Mission mission = userMission.getMission();
        return MissionSummaryDto.builder()
                .missionId(mission.getId())
                .todo(mission.getTodo())
                .score(mission.getScore())
                .deadline(mission.getDeadline())
                .build();
    }

    // Page<UserMission> → PageResponse<MissionSummaryDto>
    public static PageResponse<MissionSummaryDto> toMissionSummaryPageFromUserMission(Page<UserMission> userMissionPage) {
        List<MissionSummaryDto> content = userMissionPage.getContent().stream()
                .map(MissionConverter::toMissionSummaryDto)
                .collect(Collectors.toList());

        return PageResponse.<MissionSummaryDto>builder()
                .content(content)
                .page(userMissionPage.getNumber() + 1)
                .size(userMissionPage.getSize())
                .totalElements(userMissionPage.getTotalElements())
                .totalPages(userMissionPage.getTotalPages())
                .last(userMissionPage.isLast())
                .build();
    }
}