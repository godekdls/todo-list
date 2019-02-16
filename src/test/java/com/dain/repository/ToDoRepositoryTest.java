package com.dain.repository;

import com.dain.MockToDoFactory;
import com.dain.TestConfig;
import com.dain.model.Status;
import com.dain.model.ToDo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ToDoRepositoryTest extends TestConfig {

    @Autowired
    private ToDoRepository toDoRepository;

    @Test
    public void 할일을_생성할수_있다() {
        // given
        ToDo toDo = MockToDoFactory.getMockToDo();
        toDo.setId(null);

        // when
        ToDo result = this.toDoRepository.save(toDo);

        // then
        assertThat(result.getId(), is(notNullValue()));
    }

    @Test
    public void 할일을_조회할수_있다() {
        // given
        ToDo toDo = MockToDoFactory.getMockToDo();
        Long id = this.toDoRepository.save(toDo).getId();

        // when
        Optional<ToDo> result = this.toDoRepository.findById(id);

        // then
        assertThat(result.isPresent(), is(true));
    }

    @Test
    public void 할일을_수정할수_있다() {
        // given
        ToDo toDo = MockToDoFactory.getMockToDo();
        toDo = this.toDoRepository.save(toDo);
        toDo.setDescription("updated");

        // when
        ToDo result = this.toDoRepository.save(toDo);

        // then
        assertThat(result.getDescription(), is("updated"));
    }

    @Test
    public void 할일을_완료처리할수_있다() {
        // given
        ToDo toDo = MockToDoFactory.getMockToDo();
        toDo.open();
        toDo = this.toDoRepository.save(toDo);

        // when
        toDo.complete();
        ToDo result = this.toDoRepository.save(toDo);

        // then
        assertThat(result.getStatus(), is(Status.closed));
    }

    @Test
    public void 할일을_열수_있다() {
        // given
        ToDo toDo = MockToDoFactory.getMockToDo();
        toDo.complete();
        toDo =  this.toDoRepository.save(toDo);

        // when
        toDo.open();
        ToDo result = this.toDoRepository.save(toDo);

        // then
        assertThat(result.getStatus(), is(Status.open));
    }

    @Test
    public void 할일을_삭제할수_있다() {
        // given
        ToDo toDo = MockToDoFactory.getMockToDo();
        toDo = this.toDoRepository.save(toDo);

        // when
        this.toDoRepository.deleteById(toDo.getId());
    }

    @Test
    public void 목록을_조회할수_있다() {
        // given
        int currentPage = 0;
        int display = 10;
        PageRequest pageRequest = PageRequest.of(currentPage, display);

        // when
        Page<ToDo> toDos = this.toDoRepository.findAll(pageRequest);

        // then
        assertThat(toDos.getContent(), hasSize(greaterThan(0)));
    }

    @Test
    public void 목록을_정렬해서_구할수있다() {
        // given
        int currentPage = 0;
        int display = 2;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(currentPage, display, sort);

        // when
        Page<ToDo> toDos = this.toDoRepository.findAll(pageRequest);

        // then
        assertThat(toDos.getContent(), hasSize(display));
        assertThat(toDos.getContent().get(0).getId(), greaterThan(toDos.getContent().get(1).getId()));
    }

    @Test
    public void 전체카운트를_구할수있다() {
        // when
        long totalCount = this.toDoRepository.count();

        // then
        assertThat(totalCount, is(greaterThan(0l)));
    }

}
