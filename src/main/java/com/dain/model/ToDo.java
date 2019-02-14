package com.dain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class ToDo {

    @Setter
    private Long id;
    private String description;
    private String createDateTime; // TODO date type
    private String updateDateTime; // TODO date type
    private Status status; // TODO default value
    private List<Long> references;

    public void complete() {
        this.status = Status.closed;
    }

}
