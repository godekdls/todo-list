package com.dain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ToDo {

    @Setter
    private Long id;
    private String description;
    private String createDateTime; // TODO date type
    private String updateDateTime; // TODO date type
    private Status status = Status.open;
    private Set<Long> references = new HashSet<>();

    public void open() {
        this.status = Status.open;
    }

    public void complete() {
        this.status = Status.closed;
    }

}
