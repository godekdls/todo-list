package com.dain.repository;

import com.dain.model.ToDo;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ToDoRepository extends PagingAndSortingRepository<ToDo, Long> {

}
