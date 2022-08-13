package com.jyeol.dividend.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserError {
    NOT_EXIST_USER(HttpStatus.BAD_REQUEST.value(), "존재하지 않는 유저입니다."),
    ALREADY_EXIST_USER(HttpStatus.BAD_REQUEST.value(), "이미 존재하는 유저명입니다."),
    PASSWORD_MIS_MATCH(HttpStatus.BAD_REQUEST.value(), "비밀번호가 일치하지 않습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST.value(), "토큰이 유효하지 않습니다."),
    ;

    private final int statusCode;
    private final String message;
}
