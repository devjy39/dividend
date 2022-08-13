package com.jyeol.dividend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AbstractException.class)
    protected ResponseEntity<ErrorResponse> abstractExceptionHandler(AbstractException e) {
        return ResponseEntity
                .status(e.getStatusCode())
                .body(ErrorResponse.builder()
                        .code(e.getStatusCode())
                        .message(e.getMessage())
                        .build());
    }

}
