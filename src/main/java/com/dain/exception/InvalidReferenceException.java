package com.dain.exception;

import com.dain.controller.model.ErrorCause;

public class InvalidReferenceException extends RuntimeException {

    public InvalidReferenceException(ErrorCause cause) {
        super(cause.message);
    }

}
