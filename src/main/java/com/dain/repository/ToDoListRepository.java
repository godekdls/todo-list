package com.dain.repository;

import com.dain.model.Status;
import com.dain.model.ToDo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
public class ToDoListRepository {

    private List<ToDo> mockList;

    @SneakyThrows
    @PostConstruct
    public void initMock() {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "[{\"id\":1,\"description\":\"집안일\",\"createDateTime\":\"2018-04-01 10:00:00\",\"updateDateTime\":\"2018-04-01 13:00:00\",\"status\":\"closed\"},{\"id\":2,\"description\":\"빨래\",\"createDateTime\":\"2018-04-01 11:00:00\",\"updateDateTime\":\"2018-04-01 11:00:00\",\"status\":\"open\",\"references\":[1]},{\"id\":3,\"description\":\"청소\",\"createDateTime\":\"2018-04-01 12:00:00\",\"updateDateTime\":\"2018-04-01 13:00:00\",\"status\":\"open\",\"references\":[1]},{\"id\":4,\"description\":\"방청소\",\"createDateTime\":\"2018-04-01 12:00:00\",\"updateDateTime\":\"2018-04-01 13:00:00\",\"status\":\"open\",\"references\":[1,3]}]";
        this.mockList = objectMapper.readValue(json, new TypeReference<List<ToDo>>() {
        });
    }

    public Long create(ToDo todo) {
        // todo
        return 1l;
    }

    public ToDo read(Long id) {
        // todo
        return list(1, 10).get(0);
    }

    public int update(ToDo todo) {
        // todo
        return 1;
    }

    public int updateStatus(Long id, Status status) {
        // todo
        return 1;
    }

    public int delete(Long id) {
        // todo
        return 1;
    }

    public List<ToDo> list(int currentPage, int display) {
        // todo
        return this.mockList;
    }

    public int getTotalCount() {
        // todo
        return 100;
    }
}
