package com.dain.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private ErrorCause cause;

    public String getMessage() {
        return cause.message;
    }

}
