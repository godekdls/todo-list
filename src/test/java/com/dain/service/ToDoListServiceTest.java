package com.dain.service;

import com.dain.MockToDoFactory;
import com.dain.model.ToDo;
import com.dain.repository.ToDoListRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ToDoListServiceTest {

    @InjectMocks
    private ToDoListService toDoListService;
    @Mock
    private ToDoListRepository toDoListRepository;

    @Test
    public void 할일을_생성할수_있다() {
        // given
        when(toDoListRepository.create(any(ToDo.class))).thenReturn(123l);
        ToDo toDo = MockToDoFactory.getMockToDo();

        // when
        Long id = this.toDoListService.create(toDo);

        // then
        assertThat(id, is(123l));
    }

    @Test
    public void 할일을_조회할수_있다() {
        // given
        ToDo mockToDo = MockToDoFactory.getMockToDo();
        when(toDoListRepository.read(anyLong())).thenReturn(mockToDo);
        Long id = mockToDo.getId();

        // when
        ToDo toDo = this.toDoListService.read(id);

        // then
        assertThat(toDo.getId(), is(id));
        assertThat(toDo.getDescription(), is(notNullValue()));
    }

    @Test
    public void 할일을_수정할수_있다() {
        // given
        when(toDoListRepository.update(any(ToDo.class))).thenReturn(1);
        ToDo toDo = MockToDoFactory.getMockToDo();

        // when
        int num = this.toDoListService.update(toDo);

        // then
        assertThat(num, is(1));
    }

    @Test
    public void 할일을_삭제할수_있다() {
        // given
        when(toDoListRepository.delete(anyLong())).thenReturn(1);
        Long id = MockToDoFactory.getMockToDo().getId();

        // when
        int num = this.toDoListService.delete(id);

        // then
        assertThat(num, is(1));
    }

    @Test
    public void 전체목록을_조회할수_있다() {
        // given
        List<ToDo> mockToDoList = MockToDoFactory.getMockToDoList();
        when(toDoListRepository.listAll()).thenReturn(mockToDoList);

        // when
        List<ToDo> toDos = this.toDoListService.listAll();

        // then
        assertThat(toDos, hasSize(greaterThan(0)));
    }

}
