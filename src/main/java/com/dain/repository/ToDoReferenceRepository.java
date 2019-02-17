package com.dain.repository;

import com.dain.model.ToDoReference;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ToDoReferenceRepository extends CrudRepository<ToDoReference, Long> {

    List<ToDoReference> findAllByReferredId(Long referredId);

    @Transactional
    void deleteAllByReferredId(Long referredId);

}
