package com.example.umc9th.domain.mission.controller;


import com.example.umc9th.domain.mission.constant.MissionSuccessCode;
import com.example.umc9th.domain.mission.dto.*;
import com.example.umc9th.domain.mission.service.MissionService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.dto.PageResponse;
import com.example.umc9th.global.web.page.OneBasedPageable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MissionController {

    private final MissionService missionService;

     // 3) 가게에 미션 추가하기
     // POST /api/v1/store/{storeId}/mission
    @PostMapping("/store/{storeId}/mission")
    @Operation(
            summary = "가게에 미션 추가",
            description = "특정 가게에 새로운 미션을 등록합니다."
    )
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
    @Operation(
            summary = "미션 도전하기",
            description = "특정 미션을 유저의 도전 중인 미션(user_mission)에 추가합니다."
    )
    public ApiResponse<UserMissionChallengeResponse> challengeMission(
            @PathVariable Long missionId,
            @RequestBody @Valid UserMissionChallengeRequest request
    ) {
        UserMissionChallengeResponse response = missionService.challengeMission(missionId, request);
        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_CHALLENGE_SUCCESS, response);
    }

    // ✅ 5) 특정 가게의 미션 목록 조회 (페이징)
    // GET /api/v1/store/{storeId}/missions?page=1
    @GetMapping("/store/{storeId}/missions")
    @Operation(
            summary = "가게의 미션 목록 조회",
            description = """
                    - 특정 가게에 등록된 미션 목록을 페이지네이션하여 조회합니다.
                    - page는 1 이상의 정수입니다. (쿼리 스트링 ?page=1)
                    - 한 페이지에 10개씩 고정으로 조회합니다.
                    """
    )
    public ApiResponse<PageResponse<MissionSummaryDto>> getStoreMissions(
            @PathVariable
            Long storeId,

            @OneBasedPageable
            @Parameter(description = "1 이상의 페이지 번호 (?page=1 부터 시작)")
            Pageable pageable
    ) {
        PageResponse<MissionSummaryDto> response = missionService.getStoreMissions(storeId, pageable);
        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_LIST_SUCCESS, response);
    }
}