package com.dain.controller;

import com.dain.MockToDoFactory;
import com.dain.ResourceFileReader;
import com.dain.model.ToDo;
import com.dain.service.ToDoListService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ToDoListControllerTest {

    @InjectMocks
    private ToDoListController toDoListController;
    @Mock
    private ToDoListService toDoListService;
    private MockMvc mockMvc;

    @Before
    public void initMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(toDoListController)
//                .setControllerAdvice(new ExceptionAdvice()) // TODO
                .build();
    }

    @Test
    public void create() throws Exception {
        when(toDoListService.create(any(ToDo.class))).thenReturn(123l);

        String json = ResourceFileReader.readFile("todo.json");
        URI uri = UriComponentsBuilder.fromPath("/todos")
                .build().toUri();

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(123)));
    }

    @Test
    public void read() throws Exception {
        when(toDoListService.read(anyLong())).thenReturn(MockToDoFactory.getMockToDo());

        URI uri = UriComponentsBuilder.fromPath("/todos/1")
                .build().toUri();

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(notNullValue())));
    }

    @Test
    public void update() throws Exception {
        when(toDoListService.update(any(ToDo.class))).thenReturn(1);

        String json = ResourceFileReader.readFile("todo.json");
        URI uri = UriComponentsBuilder.fromPath("/todos/1")
                .build().toUri();

        mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void delete() throws Exception {
        when(toDoListService.delete(anyLong())).thenReturn(1);
        URI uri = UriComponentsBuilder.fromPath("/todos/1")
                .build().toUri();

        mockMvc.perform(MockMvcRequestBuilders.delete(uri))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void listAll() throws Exception {
        when(toDoListService.listAll()).thenReturn(MockToDoFactory.getMockToDoList());
        URI uri = UriComponentsBuilder.fromPath("/todos")
                .build().toUri();

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(notNullValue())))
                .andExpect(jsonPath("$[0].description", is(notNullValue())));
    }

}
