package com.dain.controller;

import com.dain.controller.model.CreateResponse;
import com.dain.model.ToDo;
import com.dain.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ToDoListController {

    @Autowired
    private ToDoListService toDoListService;

    @PostMapping("/todos")
    public CreateResponse create(@RequestBody ToDo todo) {
        Long id = this.toDoListService.create(todo);
        return new CreateResponse(id);
    }

    @GetMapping("/todos/{id}")
    public ToDo read(@PathVariable Long id) {
        return this.toDoListService.read(id);
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody ToDo todo) {
        todo.setId(id);
        this.toDoListService.update(todo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.toDoListService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/todos")
    public List<ToDo> listAll() {
        return this.toDoListService.listAll();
    }


}
