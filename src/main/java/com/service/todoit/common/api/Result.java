package com.service.todoit.common.api;

import com.service.todoit.common.code.ResponseCode;
import com.service.todoit.common.code.ResponseCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer resultCode;
    private String resultMessage;
    private String resultDescription;

    public static Result OK(String description) {
        return Result.builder()
                .resultCode(ResponseCode.OK.getStatusCode())
                .resultMessage(ResponseCode.OK.getStatusMessage())
                .resultDescription(description)
                .build();
    }

    public static Result ERROR(ResponseCodeIfs errorCode) {
        return Result.builder()
                .resultCode(errorCode.getStatusCode())
                .resultMessage(errorCode.getStatusMessage())
                .resultDescription(null)
                .build();
    }

    public static Result ERROR(ResponseCodeIfs errorCode, String errorDescription) {
        return Result.builder()
                .resultCode(errorCode.getStatusCode())
                .resultMessage(errorCode.getStatusMessage())
                .resultDescription(errorDescription)
                .build();
    }
}
