package com.service.todoit.common.exception;

import com.service.todoit.common.code.ResponseCodeIfs;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{

    private final ResponseCodeIfs responseCodeIfs;
    private final String errorDescription;

    public ApiException(ResponseCodeIfs responseCodeIfs, String errorDescription) {
        this.responseCodeIfs = responseCodeIfs;
        this.errorDescription = errorDescription;
    }
    public ApiException(ResponseCodeIfs responseCodeIfs, Exception e) {
        this.responseCodeIfs = responseCodeIfs;
        this.errorDescription = responseCodeIfs.getStatusMessage();
    }
}