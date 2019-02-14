package com.dain.controller;

import com.dain.ResourceFileReader;
import com.dain.TestMvcConfig;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ToDoListIntegrationTest extends TestMvcConfig {

    @Test
    public void create() throws Exception {
        String json = ResourceFileReader.readFile("todo.json");

        URI uri = UriComponentsBuilder.fromPath("/todos")
                .build().toUri();

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(jsonPath("$.id", is(notNullValue())));
    }

    @Test
    public void read() throws Exception {
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
        URI uri = UriComponentsBuilder.fromPath("/todos/1")
                .build().toUri();

        mockMvc.perform(MockMvcRequestBuilders.delete(uri))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void listAll() throws Exception {
        URI uri = UriComponentsBuilder.fromPath("/todos")
                .build().toUri();

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(notNullValue())))
                .andExpect(jsonPath("$[0].description", is(notNullValue())));
    }

}
