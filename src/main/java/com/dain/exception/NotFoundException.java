package com.dain.exception;

import com.dain.controller.model.ErrorCause;
import lombok.Getter;

@Getter
public class NotFoundException extends AbstractRunTimeException {

    private Long id;

    public NotFoundException(Long id) {
        super(ErrorCause.TO_DO_NOT_FOUND);
        this.id = id;
    }

}
