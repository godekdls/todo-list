package com.dain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
public class ToDo {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(columnDefinition = "varchar(100)", nullable = false)
    private String description;

    @Column(updatable = false, nullable = false)
    private Date createDateTime;

    @Column(nullable = false)
    private Date updateDateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) default 'open'")
    private Status status;

    @ElementCollection(targetClass = Long.class)
    @CollectionTable(name = "to_do_references", joinColumns = @JoinColumn(name = "to_do_id"))
    @Column(name = "reference")
    private Set<Long> references = new HashSet<>();

    public void open() {
        this.status = Status.open;
    }

    public void complete() {
        this.status = Status.closed;
    }

    @PrePersist
    protected void onCreate() {
        this.createDateTime = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateDateTime = new Date();
    }

}
