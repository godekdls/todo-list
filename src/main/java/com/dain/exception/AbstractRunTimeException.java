package com.dain.exception;

import com.dain.controller.model.ErrorCause;
import lombok.Getter;

@Getter
public abstract class AbstractRunTimeException extends RuntimeException {

    private ErrorCause errorCause;

    public AbstractRunTimeException(ErrorCause errorCause) {
        super(errorCause.message);
        this.errorCause = errorCause;
    }

}
