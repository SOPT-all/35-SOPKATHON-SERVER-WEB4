package com.sopt.sopkathon.common.response;

import com.sopt.sopkathon.common.response.message.FailMessage;
import com.sopt.sopkathon.common.response.message.SuccessMessage;
import org.springframework.http.ResponseEntity;

public class ApiResponseUtil {
    public static ResponseEntity<BaseResponse<?>> success(final SuccessMessage successMessage) {
        return org.springframework.http.ResponseEntity.status(successMessage.getHttpStatus())
                .body(BaseResponse.of(successMessage));
    }

    public static <T> ResponseEntity<BaseResponse<?>> success(final SuccessMessage successMessage, final T data) {
        return ResponseEntity.status(successMessage.getHttpStatus())
                .body(BaseResponse.of(successMessage, data));
    }

    public static ResponseEntity<BaseResponse<?>> failure(final FailMessage failMessage) {
        return ResponseEntity.status(failMessage.getHttpStatus())
                .body(BaseResponse.of(failMessage));
    }

    public static ResponseEntity<BaseResponse<?>> failure(final FailMessage failMessage, final String message) {
        return ResponseEntity
                .status(failMessage.getHttpStatus())
                .body(BaseResponse.of(failMessage.getCode(), message));
    }
}
