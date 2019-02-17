package com.dain.model;

import com.dain.service.serialize.ReferenceDeserializer;
import com.dain.service.serialize.ReferenceSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "to_do_references")
@JsonSerialize(using = ReferenceSerializer.class)
@JsonDeserialize(using = ReferenceDeserializer.class)
public class ToDoReference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Column(nullable = false)
    private Long referredId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "to_do_id", nullable = false)
    private ToDo toDo;

    @Override
    public String toString() {
        return referredId.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ToDoReference) {
            return this.referredId == ((ToDoReference) obj).referredId;
        }
        return false;
    }

}
