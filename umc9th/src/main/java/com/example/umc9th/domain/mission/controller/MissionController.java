package com.example.umc9th.domain.mission.controller;


import com.example.umc9th.domain.mission.constant.MissionSuccessCode;
import com.example.umc9th.domain.mission.dto.*;
import com.example.umc9th.domain.mission.service.MissionService;
import com.example.umc9th.global.apiPayload.ApiResponse;
import com.example.umc9th.global.dto.PageResponse;
import com.example.umc9th.global.dto.SliceResponse;
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

     // 가게에 미션 추가하기
     // POST /api/v1/store/{storeId}/mission
    @PostMapping("/mission/{storeId}")
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

    // 특정 가게의 미션 목록 조회 (페이징)
    // GET /api/v1/missions/{storeId}?page=1
    @GetMapping("/missions/{storeId}")
    @Operation(
            summary = "가게의 미션 목록 조회",
            description = """
                    - 특정 가게에 등록된 미션 목록을 조회합니다.
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

    // 사용자가 진행 중인 미션 목록 조회
    // GET /api/v1/missions/{memberId}?page=1
    @GetMapping("/missions/{memberId}")
    @Operation(
            summary = "사용자가 진행 중인 미션 목록 조회 (Page 기반)",
            description = """
                    - 특정 사용자가 현재 진행 중인 미션 목록을 조회.
                    
                    - 현재는 PathVariable의 memberId로 조회하지만,
                      추후 로그인 기능 구현 시에는 
                      토큰에서 memberId를 추출하는 방식으로 변경 필요.
                    
                    - page는 1 이상의 정수입니다. (쿼리 스트링 ?page=1)
                    - 한 페이지에 10개씩 고정으로 조회합니다.
                    """
    )
    public ApiResponse<PageResponse<MissionSummaryDto>> getOngoingMissions(
            @PathVariable
            @Parameter(description = "회원 ID. 현재는 PathVariable로 전달되지만, 추후에는 토큰에서 추출 예정")
            Long memberId,

            @OneBasedPageable
            @Parameter(description = "1 이상의 페이지 번호 (?page=1 부터 시작)")
            Pageable pageable
    ) {
        PageResponse<MissionSummaryDto> response = missionService.getOngoingMissions(memberId, pageable);
        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_ONGOING_LIST_SUCCESS, response);
    }

    @GetMapping("/missions/{memberId}/slice")
    @Operation(
            summary = "사용자가 진행 중인 미션 목록 조회 (Slice 기반)",
            description = """
                    특정 사용자가 현재 진행 중인 미션 목록을 Slice 기반으로 조회합니다.
                    
                    - Page 기반 API(/api/v1/missions/{memberId})와 달리,
                      전체 개수(totalElements, totalPages)를 조회하지 않고
                      hasNext 여부만 제공합니다.
                    
                    - 현재는 PathVariable의 memberId로 조회하지만,
                      추후 로그인/인증 기능 구현 시에는 
                      토큰에서 memberId를 추출하는 방식으로 변경 필요.
                    """
    )
    public ApiResponse<SliceResponse<MissionSummaryDto>> getOngoingMissionsSlice(
            @PathVariable
            @Parameter(description = "회원 ID. 현재는 PathVariable로 전달되지만, 추후에는 토큰에서 추출 예정")
            Long memberId,

            @OneBasedPageable
            @Parameter(description = "1 이상의 페이지 번호 (?page=1 부터 시작)")
            Pageable pageable
    ) {
        SliceResponse<MissionSummaryDto> response =
                missionService.getOngoingMissionsSlice(memberId, pageable);

        // 굳이 따로 코드 만들지 않고 기존 성공 코드 재사용해도 무방
        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_ONGOING_LIST_SUCCESS, response);
    }

     // 가게의 미션을 도전 중인 미션에 추가(미션 도전하기)
     // POST /api/v1/missions/{missionId}/challenge
    @PostMapping("/missions/{missionId}/challenge")
    @Operation(
            summary = "미션 도전하기",
            description = "특정 미션을 유저의 도전 중인 미션(user_mission)에 추가합니다."
    )
    public ApiResponse<UserMissionChallengeResponse> challengeMission(
            @PathVariable
            @Parameter(description = "회원 ID. 현재는 PathVariable로 전달되지만, 추후에는 토큰에서 추출 예정")
            Long missionId,

            @RequestBody @Valid UserMissionChallengeRequest request
    ) {
        UserMissionChallengeResponse response = missionService.challengeMission(missionId, request);
        return ApiResponse.onSuccess(MissionSuccessCode.MISSION_CHALLENGE_SUCCESS, response);
    }


}