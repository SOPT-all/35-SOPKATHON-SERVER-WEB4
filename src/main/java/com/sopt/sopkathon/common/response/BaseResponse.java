package com.sopt.sopkathon.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sopt.sopkathon.common.response.message.ApiMessage;
import com.sopt.sopkathon.common.response.message.SuccessMessage;

@JsonPropertyOrder({"code", "message", "data"})
public class BaseResponse<T> {
    private final int code;
    private final String message;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private final T data;

    private BaseResponse(final int status, final String message, final T data) {
        this.code = status;
        this.message = message;
        this.data = data;
    }

    public static BaseResponse<?> of(final ApiMessage apiMessage) {
        return builder()
                .code(apiMessage.getCode())
                .message(apiMessage.getMessage())
                .build();
    }

    public static <T> BaseResponse<?> of(SuccessMessage successMessage, T data) {
        return builder()
                .code(successMessage.getCode())
                .message(successMessage.getMessage())
                .data(data)
                .build();
    }

    public static BaseResponse<?> of(final int code, final String message) {
        return builder()
                .code(code)
                .message(message)
                .build();
    }

    private static class Builder<T> {
        private int code;
        private String message;
        private T data;

        private Builder<T> code(final int code) {
            this.code = code;
            return this;
        }

        private Builder<T> message(final String message) {
            this.message = message;
            return this;
        }

        private Builder<T> data(final T data) {
            this.data = data;
            return this;
        }

        private BaseResponse<T> build() {
            return new BaseResponse<>(code, message, data);
        }
    }

    private static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
