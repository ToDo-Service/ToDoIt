package com.service.todoit.common.handler;

import com.service.todoit.common.api.Api;
import com.service.todoit.common.code.ResponseCodeIfs;
import com.service.todoit.common.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = 0)
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Api<Object>> apiException(
            ApiException e
    ) {

        ResponseCodeIfs errorCode = e.getResponseCodeIfs();

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(Api.ERROR(errorCode, e.getErrorDescription()));
    }
}