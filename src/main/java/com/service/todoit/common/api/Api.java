package com.service.todoit.common.api;


import com.service.todoit.common.code.ResponseCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {

    private Result result;
    private T body;


    public static <T> Api<T> OK(String description, T data) {
        return Api.<T>builder()
                .result(Result.OK(description))
                .body(data)
                .build();
    }

    public static <T> Api<T> ERROR(ResponseCodeIfs errorCode) {
        return Api.<T>builder()
                .result(Result.ERROR(errorCode))
                .body(null)
                .build();
    }

    public static <T> Api<T> ERROR(ResponseCodeIfs errorCode, String errorDescription) {
        return Api.<T>builder()
                .result(Result.ERROR(errorCode, errorDescription))
                .body(null)
                .build();
    }

}
