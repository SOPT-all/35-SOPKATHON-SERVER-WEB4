package com.sopt.sopkathon.common.exception;

import com.sopt.sopkathon.common.response.message.FailMessage;

public class CustomException extends RuntimeException {
    private final FailMessage failMessage;

    public CustomException(FailMessage failMessage) {
        super(failMessage.getMessage());
        this.failMessage = failMessage;
    }

    public FailMessage getFailMessage() {
        return failMessage;
    }
}
