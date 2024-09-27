package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestMapperTest {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    void mapToTaskTest() {
        // given
        TaskDto taskDto = new TaskDto(1L, "First task","Content of the first task");

        // when
        Task task = taskMapper.mapToTask(taskDto);

        // then
        assertEquals("First task", task.getTitle());
    }

    @Test
    void mapToTaskDtoTest() {
        // given
        Task task = new Task(2L, "Second task", "Content of the second task");

        // when
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        // then
        assertEquals("Second task", taskDto.getTitle());
    }

    @Test
    void mapToTaskDtoList() {
        // given
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(3L, "Third task", "Content of the third task"));

        // when
        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(tasks);

        // then
        assertEquals("Third task", taskDtos.get(0).getTitle());
    }
}
