package com.dain.exception;

import com.dain.controller.model.ErrorCause;

public class NotClosableException extends AbstractRunTimeException {

    public NotClosableException() {
        super(ErrorCause.NOT_CLOSEABLE);
    }

}
