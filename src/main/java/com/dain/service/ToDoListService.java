package com.dain.service;

import com.dain.exception.NotFoundException;
import com.dain.model.ToDo;
import com.dain.repository.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoListService {

    @Autowired
    private ToDoListRepository toDoListRepository;

    public Long create(ToDo todo) {
        Long id = this.toDoListRepository.create(todo);
        return id;
    }

    public ToDo read(Long id) {
        ToDo read = this.toDoListRepository.read(id);
        if (read == null) {
            throw new NotFoundException("ToDo { id : " + id + " } doesn't exist");
        }
        return read;
    }

    public int update(ToDo todo) {
        int cnt = this.toDoListRepository.update(todo);
        if (cnt < 1) {
            throw new NotFoundException("ToDo { id : " + todo.getId() + " } doesn't exist");
        }
        return cnt;
    }

    public int delete(Long id) {
        int cnt = this.toDoListRepository.delete(id);
        if (cnt < 1) {
            throw new NotFoundException("ToDo { id : " + id + " } doesn't exist");
        }
        return cnt;
    }

    public List<ToDo> list(int currentPage, int display) {
        return this.toDoListRepository.list(currentPage, display);
    }

    public int getTotalCount() {
        return this.toDoListRepository.getTotalCount();
    }

}
