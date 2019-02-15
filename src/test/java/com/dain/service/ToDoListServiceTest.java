package com.dain.service;

import com.dain.MockToDoFactory;
import com.dain.exception.NotFoundException;
import com.dain.model.Status;
import com.dain.model.ToDo;
import com.dain.repository.ToDoListRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.*;
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

    @Test(expected = NotFoundException.class)
    public void 할일이_존재하지않는경우_조회할수없다() {
        // given
        when(toDoListRepository.read(anyLong())).thenReturn(null);
        Long id = 123l;

        // when
        this.toDoListService.read(id);
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

    @Test(expected = NotFoundException.class)
    public void 할일이_존재하지않는경우_수정할수없다() {
        // given
        when(toDoListRepository.update(any(ToDo.class))).thenReturn(0);
        ToDo toDo = new ToDo();

        // when
        this.toDoListService.update(toDo);
    }

    @Test
    public void 할일의_상태를_변경할수있다() {
        // given
        when(toDoListRepository.updateStatus(anyLong(), any(Status.class))).thenReturn(1);
        ToDo toDo = MockToDoFactory.getMockToDo();

        // when
        int num = this.toDoListService.updateStatus(toDo);

        // then
        assertThat(num, is(1));
    }

    @Test(expected = NotFoundException.class)
    public void 할일이_존재하지않는경우_상태를_수정할수없다() {
        // given
        when(toDoListRepository.updateStatus(anyLong(), any(Status.class))).thenReturn(0);
        ToDo toDo = new ToDo();

        // when
        this.toDoListService.updateStatus(toDo);
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

    @Test(expected = NotFoundException.class)
    public void 할일이_존재하지않는경우_삭제할수없다() {
        // given
        when(toDoListRepository.delete(anyLong())).thenReturn(0);
        Long id = 123l;

        // when
        this.toDoListService.delete(id);
    }

    @Test
    public void 목록을_조회할수_있다() {
        // given
        List<ToDo> mockToDoList = MockToDoFactory.getMockToDoList();
        when(toDoListRepository.list(anyInt(), anyInt())).thenReturn(mockToDoList);
        int currentPage = 1;
        int display = 10;

        // when
        List<ToDo> toDos = this.toDoListService.list(currentPage, display);

        // then
        assertThat(toDos, hasSize(greaterThan(0)));
    }

    @Test
    public void 데이터가없을경우_emptyList를_리턴한다() {
        // given
        when(toDoListRepository.list(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        int currentPage = 1;
        int display = 10;

        // when
        List<ToDo> toDos = this.toDoListService.list(currentPage, display);

        // then
        assertThat(toDos, hasSize(0));
    }

    @Test
    public void 전체카운트를_구한다() {
        // given
        when(toDoListRepository.getTotalCount()).thenReturn(100);

        // when
        long totalCount = this.toDoListService.getTotalCount();

        // then
        assertThat(totalCount, is(100l));
    }

}
