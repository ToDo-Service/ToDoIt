package com.service.todoit.common.handler;

import com.service.todoit.common.api.Api;
import com.service.todoit.common.code.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@Order(value = Integer.MAX_VALUE) // 제일 높은 값으로 가장 마지막 순서
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Api<Object>> exception(
            Exception e
    ) {
        log.error("",e);
        return ResponseEntity.status(500)
                .body(Api.ERROR(ResponseCode.SERVER_ERROR));
    }
}
