package com.dain.repository;

import com.dain.model.ToDoReference;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface ToDoReferenceRepository extends CrudRepository<ToDoReference, Long> {

    @Transactional
    void deleteAllByReferredId(Long referredId);

}
