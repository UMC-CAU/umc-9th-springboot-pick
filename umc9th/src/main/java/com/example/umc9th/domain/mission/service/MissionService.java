package com.example.umc9th.domain.mission.service;


import com.example.umc9th.domain.member.entity.Member;
import com.example.umc9th.domain.member.repository.MemberRepository;
import com.example.umc9th.domain.mission.converter.MissionConverter;
import com.example.umc9th.domain.mission.converter.UserMissionConverter;
import com.example.umc9th.domain.mission.dto.MissionCreateRequest;
import com.example.umc9th.domain.mission.dto.MissionCreateResponse;
import com.example.umc9th.domain.mission.dto.UserMissionChallengeRequest;
import com.example.umc9th.domain.mission.dto.UserMissionChallengeResponse;
import com.example.umc9th.domain.mission.entity.Mission;
import com.example.umc9th.domain.mission.entity.mapping.UserMission;
import com.example.umc9th.domain.mission.repository.MissionRepository;
import com.example.umc9th.domain.mission.repository.UserMissionRepository;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.domain.store.repository.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;
    private final UserMissionRepository userMissionRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    // 3) 가게에 미션 추가하기
    public MissionCreateResponse createMission(Long storeId, MissionCreateRequest request) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Store not found. id=" + storeId));

        Mission mission = MissionConverter.toEntity(request, store);
        Mission saved = missionRepository.save(mission);

        return MissionConverter.toCreateResponse(saved);
    }

    // 4) 미션 도전하기 (UserMission 추가)
    public UserMissionChallengeResponse challengeMission(Long missionId, UserMissionChallengeRequest request) {

        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Mission not found. id=" + missionId));

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Member not found. id=" + request.getMemberId()));

        UserMission userMission = UserMissionConverter.toEntity(member, mission);
        UserMission saved = userMissionRepository.save(userMission);

        return UserMissionConverter.toChallengeResponse(saved);
    }
}