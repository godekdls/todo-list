package com.dain.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(Long id) {
        super("ToDo { id : " + id + " } doesn't exist");
    }

}
