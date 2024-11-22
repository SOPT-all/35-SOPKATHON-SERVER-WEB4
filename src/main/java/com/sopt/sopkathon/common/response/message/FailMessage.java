package com.sopt.sopkathon.common.response.message;

import org.springframework.http.HttpStatus;

public enum FailMessage implements ApiMessage{
    /**
     * 400 Bad Request
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST,40000, "잘못된 요청입니다."),
    BAD_REQUEST_REQUEST_BODY_VALID(HttpStatus.BAD_REQUEST, 40001, "request body 검증 실패입니다."),
    BAD_REQUEST_REQUEST_PARAM_MODELATTRI(HttpStatus.BAD_REQUEST, 40002, "request param 혹은 modelattribute 검증 실패입니다."),
    BAD_REQUEST_MISSING_PARAM(HttpStatus.BAD_REQUEST, 40003, "필수 param이 없습니다."),
    BAD_REQUEST_METHOD_ARGUMENT_TYPE(HttpStatus.BAD_REQUEST, 40004, "메서드 인자타입이 잘못되었습니다."),
    BAD_REQUEST_NOT_READABLE(HttpStatus.BAD_REQUEST, 40005, "json 오류 혹은 reqeust body 필드 오류 입니다."),

    /**
     * 401 Unauthorized
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 40100, "리소스 접근 인증 권한이 없습니다."),

    /**
     * 403 Forbidden
     */
    FORBIDDEN(HttpStatus.FORBIDDEN, 40300, "리소스 접근 인가 권한이 없습니다."),

    /**
     * 404 Not Found
     */
    NOT_FOUND_ENTITY(HttpStatus.NOT_FOUND,40400, "대상을 찾을 수 없습니다."),
    NOT_FOUND_API(HttpStatus.NOT_FOUND, 40401, "잘못된 API입니다."),

    /**
     * 405 Method Not Allowed
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, 40500, "잘못된 HTTP method 요청입니다."),

    /**
     * 409 Conflict
     */
    CONFLICT(HttpStatus.CONFLICT, 40900, "이미 존재하는 리소스입니다."),
    INTEGRITY_CONFLICT(HttpStatus.CONFLICT, 40901, "데이터 무결성 위반입니다."),

    /**
     * 500 Internal Server Error
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,50000, "서버 내부 오류입니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    private FailMessage(final HttpStatus httpStatus, final int code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

