package com.dain.service;

import com.dain.MockToDoFactory;
import com.dain.exception.InvalidReferenceException;
import com.dain.exception.NotClosableException;
import com.dain.exception.NotFoundException;
import com.dain.model.ToDo;
import com.dain.model.ToDoReference;
import com.dain.repository.ToDoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ToDoListServiceTest {

    @InjectMocks
    private ToDoListService toDoListService;
    @Mock
    private ToDoRepository toDoRepository;

    @Test
    public void 할일을_생성할수_있다() {
        // given
        ToDo mockResult = MockToDoFactory.getMockToDo();
        mockResult.setId(123l);
        when(toDoRepository.save(any(ToDo.class))).thenReturn(mockResult);

        ToDo request = MockToDoFactory.getMockToDo();
        request.setId(null);

        // when
        Long id = this.toDoListService.create(request);

        // then
        assertThat(id, is(123l));
    }

    @Test
    public void 할일을_조회할수_있다() {
        // given
        ToDo mockToDo = MockToDoFactory.getMockToDo();
        when(toDoRepository.findById(anyLong())).thenReturn(Optional.of(mockToDo));
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
        when(toDoRepository.findById(anyLong())).thenReturn(Optional.empty());
        Long id = 123l;

        // when
        this.toDoListService.read(id);
    }

    @Test
    public void 할일을_수정할수있다() {
        // given
        ToDo mockToDo = MockToDoFactory.getMockToDo();
        mockToDo.setId(1l);
        when(toDoRepository.findById(anyLong())).thenReturn(Optional.of(mockToDo));
        when(toDoRepository.save(any(ToDo.class))).thenReturn(mockToDo);

        ToDo request = MockToDoFactory.getMockToDo();

        // when
        int num = this.toDoListService.update(request);

        // then
        assertThat(num, is(1));
    }

    @Test(expected = NotFoundException.class)
    public void 할일이_존재하지않는경우_수정할수없다() {
        // given
        when(toDoRepository.findById(anyLong())).thenReturn(Optional.empty());
        ToDo toDo = new ToDo();
        toDo.setId(1l);

        // when
        this.toDoListService.update(toDo);
    }

    @Test(expected = IllegalArgumentException.class)
    public void id를_명시하지않으면_수정할수없다() {
        // given
        ToDo toDo = new ToDo();

        // when
        this.toDoListService.update(toDo);
    }

    @Test(expected = InvalidReferenceException.class)
    public void 존재하지않는_id로는_참조를_걸수없다() {
        // given
        ToDo mockToDo = MockToDoFactory.getMockToDo();
        when(toDoRepository.findById(anyLong())).thenReturn(Optional.of(mockToDo));
        when(toDoRepository.findById(999l)).thenReturn(Optional.empty());

        ToDo request = MockToDoFactory.getMockToDo();
        ToDoReference reference = new ToDoReference();
        reference.setReferredId(999l);
        request.getReferences().add(reference);

        // when
        this.toDoListService.update(request);
    }

    @Test(expected = InvalidReferenceException.class)
    public void 상호참조를_걸수없다() {
        // given
        ToDo mockToDo1 = MockToDoFactory.getMockToDo();
        mockToDo1.setId(10l);
        when(toDoRepository.findById(10l)).thenReturn(Optional.of(mockToDo1));

        ToDo mockToDo2 = MockToDoFactory.getMockToDo();
        mockToDo2.setId(20l);
        ToDoReference reference = new ToDoReference();
        reference.setReferredId(10l);
        mockToDo2.getReferences().add(reference);
        when(toDoRepository.findById(20l)).thenReturn(Optional.of(mockToDo2));

        ToDo request = MockToDoFactory.getMockToDo();
        request.setId(10l);
        reference = new ToDoReference();
        reference.setReferredId(20l);
        request.getReferences().add(reference);

        // when
        this.toDoListService.update(request);
    }

    @Test(expected = InvalidReferenceException.class)
    public void 자기자신을_참조할수없다() {
        // given
        ToDo mockToDo = MockToDoFactory.getMockToDo();
        mockToDo.setId(10l);
        when(toDoRepository.findById(10l)).thenReturn(Optional.of(mockToDo));

        ToDo request = MockToDoFactory.getMockToDo();
        request.setId(10l);
        ToDoReference reference = new ToDoReference();
        reference.setReferredId(10l);
        request.getReferences().add(reference);

        // then
        this.toDoListService.update(request);
    }

    @Test
    public void 할일의_상태를_변경할수있다() {
        // given
        ToDo mockToDo = MockToDoFactory.getMockToDo();
        when(toDoRepository.findById(anyLong())).thenReturn(Optional.of(mockToDo));
        ToDo toDo = MockToDoFactory.getMockToDo();

        // when
        int num = this.toDoListService.updateStatus(toDo.getId(), toDo.getStatus());

        // then
        assertThat(num, is(1));
    }

    @Test(expected = NotClosableException.class)
    public void 참조된할일중_열린할일이있으면_할일을_종료할수없다() {
        ToDo mockToDo1 = MockToDoFactory.getMockToDo();
        mockToDo1.setId(10l);
        ToDoReference reference = new ToDoReference();
        reference.setReferredId(20l);
        mockToDo1.getReferences().add(reference);
        mockToDo1.complete();
        when(toDoRepository.findById(10l)).thenReturn(Optional.of(mockToDo1));

        ToDo mockToDo2 = MockToDoFactory.getMockToDo();
        mockToDo2.open();
        when(toDoRepository.findById(20l)).thenReturn(Optional.of(mockToDo2));

        // when
        this.toDoListService.updateStatus(mockToDo1.getId(), mockToDo1.getStatus());
    }

    @Test(expected = NotFoundException.class)
    public void 할일이_존재하지않는경우_상태를_수정할수없다() {
        // given
        when(toDoRepository.findById(anyLong())).thenReturn(Optional.empty());
        ToDo toDo = new ToDo();
        toDo.setId(1l);

        // when
        this.toDoListService.updateStatus(toDo.getId(), toDo.getStatus());
    }

    @Test
    public void 할일을_삭제할수_있다() {
        // given
        ToDo mockToDo = MockToDoFactory.getMockToDo();
        Long id = mockToDo.getId();

        // when
        int num = this.toDoListService.delete(id);

        // then
        assertThat(num, is(1));
    }

    @Test(expected = NotFoundException.class)
    public void 할일이_존재하지않는경우_삭제할수없다() {
        // given
        doThrow(new EmptyResultDataAccessException(1)).when(toDoRepository).deleteById(anyLong());
        Long id = 123l;

        // when
        this.toDoListService.delete(id);
    }

    @Test
    public void 목록을_조회할수_있다() {
        // given
        List<ToDo> mockToDoList = MockToDoFactory.getMockToDoList();
        Page pagedList = new PageImpl(mockToDoList);
        when(toDoRepository.findAll(any(Pageable.class))).thenReturn(pagedList);
        int currentPage = 1;
        int display = 10;

        // when
        List<ToDo> toDos = this.toDoListService.list(currentPage, display).getContent();

        // then
        assertThat(toDos, hasSize(greaterThan(0)));
    }

    @Test
    public void 데이터가없을경우_emptyList를_리턴한다() {
        // given
        Page pagedList = new PageImpl(new ArrayList());
        when(toDoRepository.findAll(any(Pageable.class))).thenReturn(pagedList);
        int currentPage = 1;
        int display = 10;

        // when
        List<ToDo> toDos = this.toDoListService.list(currentPage, display).getContent();

        // then
        assertThat(toDos, hasSize(0));
    }

}
