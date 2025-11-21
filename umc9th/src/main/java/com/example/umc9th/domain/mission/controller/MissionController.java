package com.example.umc9th.domain.mission.controller;


import com.example.umc9th.domain.mission.constant.MissionSuccessCode;
import com.example.umc9th.domain.mission.dto.MissionCreateRequest;
import com.example.umc9th.domain.mission.dto.MissionCreateResponse;
import com.example.umc9th.domain.mission.dto.UserMissionChallengeRequest;
import com.example.umc9th.domain.mission.dto.UserMissionChallengeResponse;
import com.example.umc9th.domain.mission.service.MissionService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MissionController {

    private final MissionService missionService;

     // 3) 가게에 미션 추가하기
     // POST /api/v1/store/{storeId}/mission

    @PostMapping("/store/{storeId}/mission")
    public ApiResponse<MissionCreateResponse> createMission(
            @PathVariable Long storeId,
            @RequestBody @Valid MissionCreateRequest request
    ) {
        MissionCreateResponse response = missionService.createMission(storeId, request);
        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_CREATE_SUCCESS, response);
    }


     // 4) 가게의 미션을 도전 중인 미션에 추가(미션 도전하기)
     // POST /api/v1/missions/{missionId}/challenge

    @PostMapping("/missions/{missionId}/challenge")
    public ApiResponse<UserMissionChallengeResponse> challengeMission(
            @PathVariable Long missionId,
            @RequestBody @Valid UserMissionChallengeRequest request
    ) {
        UserMissionChallengeResponse response = missionService.challengeMission(missionId, request);
        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_CHALLENGE_SUCCESS, response);
    }
}