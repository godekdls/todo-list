package com.dain.exception;

public class NotClosableException extends RuntimeException {

    public NotClosableException() {
        super("can not close because referred todo is still open");
    }

}
