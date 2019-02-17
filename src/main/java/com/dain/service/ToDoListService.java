package com.dain.service;

import com.dain.exception.InvalidReferenceException;
import com.dain.exception.NotClosableException;
import com.dain.exception.NotFoundException;
import com.dain.model.Status;
import com.dain.model.ToDo;
import com.dain.model.ToDoReference;
import com.dain.repository.ToDoReferenceRepository;
import com.dain.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class ToDoListService {

    private static final Sort SORT = new Sort(Sort.Direction.DESC, "id");

    private ToDoRepository toDoRepository;
    private ToDoReferenceRepository referenceRepository;

    public ToDoListService(@Autowired ToDoRepository toDoRepository,
                           @Autowired ToDoReferenceRepository referenceRepository) {
        this.toDoRepository = toDoRepository;
        this.referenceRepository = referenceRepository;
    }

    public Long create(ToDo todo) {
        todo.getReferences().forEach(ref -> ref.setToDo(todo));
        ToDo toDo = this.toDoRepository.save(todo);
        return toDo.getId();
    }

    public ToDo read(Long id) {
        Optional<ToDo> toDo = this.toDoRepository.findById(id);
        if (!toDo.isPresent()) {
            throw new NotFoundException(id);
        }
        return toDo.get();
    }

    public int update(ToDo todo) {
        Assert.notNull(todo.getId(), "todo is can not be null in update request");
        Optional<ToDo> find = this.toDoRepository.findById(todo.getId());
        if (!find.isPresent()) {
            throw new NotFoundException(todo.getId());
        }

        List<Long> newReferences = todo.getReferences().stream()
                .filter(ref -> !find.get().getReferences().contains(ref))
                .map(ToDoReference::getReferredId)
                .collect(toList());
        checkReferable(todo.getId(), newReferences);
        if (todo.getStatus() == Status.closed) {
            checkClosable(todo);
        }
        todo.getReferences().forEach(ref -> ref.setToDo(todo));
        this.toDoRepository.save(todo);
        return 1;
    }

    public int updateStatus(Long id, Status status) {
        Assert.notNull(id, "todo is can not be null in update request");
        Optional<ToDo> find = this.toDoRepository.findById(id);
        if (!find.isPresent()) {
            throw new NotFoundException(id);
        }

        ToDo toDo = find.get();
        switch (status) {
            case open:
                toDo.open();
                break;
            case closed:
                checkClosable(toDo);
                toDo.complete();
                break;
        }
        this.toDoRepository.save(toDo);
        return 1;
    }

    public int delete(Long id) {
        try {
            this.referenceRepository.deleteAllByReferredId(id);
            this.toDoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(id);
        }
        return 1;
    }

    public Page<ToDo> list(int currentPage, int display) {
        Pageable pageable = PageRequest.of(currentPage - 1, display, SORT);
        return this.toDoRepository.findAll(pageable);
    }

    private void checkReferable(Long id, List<Long> newReferences) {
        if (newReferences.contains(id)) {
            throw new InvalidReferenceException("self reference is not available");
        }

        List<Optional<ToDo>> newReferredToDoList = newReferences.stream()
                .map(this.toDoRepository::findById)
                .collect(toList());

        if (newReferredToDoList.stream().anyMatch(toDo -> !toDo.isPresent())) {
            throw new InvalidReferenceException("referred todo is not found");
        }
        if (newReferredToDoList.stream().map(Optional::get)
                .anyMatch(list -> list.getReferences().contains(id))) {
            throw new InvalidReferenceException("cross reference is not available");
        }
    }

    private void checkClosable(ToDo toDo) {
        if (toDo.getReferences().stream()
                .map(ref -> this.toDoRepository.findById(ref.getReferredId()))
                .filter(Optional::isPresent).map(Optional::get)
                .anyMatch(ref -> ref.getStatus() == Status.open)) {
            throw new NotClosableException();
        }
    }

}
