package com.teamsparta.delivery_system.web.response

import org.springframework.http.HttpStatus

enum class ApiResponseCode(status: HttpStatus, message: String) {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    DUPLICATE_ENTITY(HttpStatus.BAD_REQUEST, "이미 해당 값이 존재합니다. 다시 입력해주세요."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "해당 값을 찾을 수 없습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 문제가 발생했습니다."),

    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다. 다시 로그인해주세요."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "올바르지 않은 토큰입니다."),
    WRONG_TYPE_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),
    ;

    val status: HttpStatus = status
    val message: String = message
}