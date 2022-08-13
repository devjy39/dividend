package com.jyeol.dividend.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CompanyError {
    INVALID_COMPANY_NAME(HttpStatus.BAD_REQUEST.value(),"존재하지 않는 회사명입니다."),
    INVALID_COMPANY_TICKER(HttpStatus.BAD_REQUEST.value(), "ticker 이름이 유효하지 않습니다."),
    ALREADY_EXIST_TICKER(HttpStatus.BAD_REQUEST.value(), "이미 존재하는 ticker입니다."),
    FAILED_SCRAP_BY_TICKER(HttpStatus.BAD_REQUEST.value(), "해당 ticker를 찾을 수 없습니다.")
    ;

    private final int statusCode;
    private final String message;
}
