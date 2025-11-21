package com.example.umc9th.domain.mission.converter;
import com.example.umc9th.domain.mission.dto.MissionCreateRequest;
import com.example.umc9th.domain.mission.dto.MissionCreateResponse;
import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.store.entity.Store;

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
}