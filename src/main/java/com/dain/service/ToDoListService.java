package com.dain.service;

import com.dain.exception.NotFoundException;
import com.dain.model.Status;
import com.dain.model.ToDo;
import com.dain.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class ToDoListService {

    private static final Sort SORT = new Sort(Sort.Direction.DESC, "id");

    @Autowired
    private ToDoRepository toDoRepository;

    public Long create(ToDo todo) {
        ToDo toDo = this.toDoRepository.save(todo);
        return toDo.getId();
    }

    public ToDo read(Long id) {
        Optional<ToDo> toDo = this.toDoRepository.findById(id);
        if (!toDo.isPresent()) {
            throw new NotFoundException("ToDo { id : " + id + " } doesn't exist");
        }
        return toDo.get();
    }

    public int update(ToDo todo) {
        Assert.notNull(todo.getId(), "todo is can not be null in update request");
        if (!this.toDoRepository.findById(todo.getId()).isPresent()) {
            throw new NotFoundException("ToDo { id : " + todo.getId() + " } doesn't exist");
        }

        this.toDoRepository.save(todo);
        return 1;
    }

    public int updateStatus(Long id, Status status) {
        Assert.notNull(id, "todo is can not be null in update request");
        Optional<ToDo> find = this.toDoRepository.findById(id);
        if (!find.isPresent()) {
            throw new NotFoundException("ToDo { id : " + id + " } doesn't exist");
        }

        ToDo toDo = find.get();
        switch (status) {
            case open:
                toDo.open();
                break;
            case closed:
                toDo.complete();
                break;
        }
        this.toDoRepository.save(toDo);
        return 1;
    }

    public int delete(Long id) {
        this.toDoRepository.deleteById(id);
        return 1;
    }

    public Page<ToDo> list(int currentPage, int display) {
        Pageable pageable = PageRequest.of(currentPage - 1, display, SORT);
        return this.toDoRepository.findAll(pageable);
    }

}
