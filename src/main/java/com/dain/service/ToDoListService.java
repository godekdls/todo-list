package com.dain.service;

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
        return this.toDoListRepository.create(todo);
    }

    public ToDo read(Long id) {
        ToDo read = this.toDoListRepository.read(id);
        if (read == null) {
            // todo exception
        }
        return read;
    }

    public int update(ToDo todo) {
        return this.toDoListRepository.update(todo);
        // todo exception
    }

    public int delete(Long id) {
        int cnt = this.toDoListRepository.delete(id);
        if (cnt != 1) {
            // todo exception
        }
        return cnt;
    }

    public List<ToDo> listAll() {
        return this.toDoListRepository.listAll();
    }

}
