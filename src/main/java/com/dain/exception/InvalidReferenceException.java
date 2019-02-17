package com.dain.exception;

import com.dain.controller.model.ErrorCause;

public class InvalidReferenceException extends AbstractRunTimeException {

    public InvalidReferenceException(ErrorCause cause) {
        super(cause);
    }

}
