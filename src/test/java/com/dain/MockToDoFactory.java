package com.dain;

import com.dain.model.ToDo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MockToDoFactory {

    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @SneakyThrows
    public static ToDo getMockToDo() {
        String json = ResourceFileReader.readFile("todo.json");
        return OBJECT_MAPPER.readValue(json, ToDo.class);
    }

    @SneakyThrows
    public static List<ToDo> getMockToDoList() {
        String json = ResourceFileReader.readFile("todo-list.json");
        return OBJECT_MAPPER.readValue(json, new TypeReference<List<ToDo>>() {
        });
    }

}
