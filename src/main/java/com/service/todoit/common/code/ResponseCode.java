package com.service.todoit.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode implements ResponseCodeIfs{
    OK(200,"성공"),
    BAD_REQUEST(400, "잘못된 요청"),
    UNAUTHORIZED(401, "인증되지 않은 사용자입니다."),
    SERVER_ERROR(500,"서버 에러"),
    NULL_POINT(512,"Null point");

    private final Integer statusCode;
    private final String statusMessage;
}
