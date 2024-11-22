package com.sopt.sopkathon.common.response.message;

import org.springframework.http.HttpStatus;

public enum SuccessMessage implements ApiMessage{
    /**
     * 200 OK
     */
    OK(HttpStatus.OK,20000, "요청이 성공했습니다."),

    /**
     * 201 Created
     */
    CREATED(HttpStatus.CREATED,200100, "요청이 성공했습니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    private SuccessMessage(final HttpStatus httpStatus, final int code, final  String message) {
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

