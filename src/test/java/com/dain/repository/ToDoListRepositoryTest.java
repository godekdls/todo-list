package com.dain.repository;

import com.dain.MockToDoFactory;
import com.dain.TestConfig;
import com.dain.model.Status;
import com.dain.model.ToDo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ToDoListRepositoryTest extends TestConfig {

    @Autowired
    private ToDoListRepository toDoListRepository;

    @Test
    public void 할일을_생성할수_있다() {
        // given
        ToDo toDo = MockToDoFactory.getMockToDo();

        // when
        Long id = this.toDoListRepository.create(toDo);

        // then
        assertThat(id, is(notNullValue()));
    }

    @Test
    public void 할일을_조회할수_있다() {
        // given
        Long id = this.toDoListRepository.create(MockToDoFactory.getMockToDo());

        // when
        ToDo toDo = this.toDoListRepository.read(id);

        // then
        assertThat(toDo, is(notNullValue()));
    }

    @Test
    public void 할일을_수정할수_있다() {
        // given
        Long id = this.toDoListRepository.create(MockToDoFactory.getMockToDo());
        ToDo toDo = this.toDoListRepository.read(id);
        toDo.complete();

        // when
        int num = this.toDoListRepository.update(toDo);

        // then
        assertThat(num, is(1));
        assertThat(this.toDoListRepository.read(id).getStatus(), is(Status.closed));
    }

    @Test
    public void 할일을_삭제할수_있다() {
        // given
        Long id = this.toDoListRepository.create(MockToDoFactory.getMockToDo());

        // when
        int num = this.toDoListRepository.delete(id);

        // then
        assertThat(num, is(1));
        assertThat(this.toDoListRepository.read(id), is(nullValue())); // todo exception?
    }

    @Test
    public void 전체목록을_조회할수_있다() {
        // when
        List<ToDo> toDos = this.toDoListRepository.listAll();

        // then
        assertThat(toDos, hasSize(greaterThan(0)));
    }

}
