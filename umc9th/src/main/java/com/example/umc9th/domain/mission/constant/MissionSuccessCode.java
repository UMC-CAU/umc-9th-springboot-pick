package com.example.umc9th.domain.mission.constant;

import com.example.umc9th.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MissionSuccessCode implements BaseSuccessCode {

    MISSION_CREATE_SUCCESS(HttpStatus.OK, "M001", "미션이 성공적으로 등록되었습니다."),
    MISSION_CHALLENGE_SUCCESS(HttpStatus.OK, "M002", "미션 도전을 시작했습니다."),
    MISSION_LIST_SUCCESS(HttpStatus.OK, "M003", "가게 미션 목록 조회 성공");

    private final HttpStatus status;
    private final String code;
    private final String message;

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}