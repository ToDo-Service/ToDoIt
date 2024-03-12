package com.service.todoit.common.exception;

import com.service.todoit.common.code.ResponseCodeIfs;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{

    private final ResponseCodeIfs responseCodeIfs;

    public ApiException(ResponseCodeIfs responseCodeIfs) {
        this.responseCodeIfs = responseCodeIfs;
    }
}