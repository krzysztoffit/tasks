package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.DbService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TaskControllerTest {


    @Autowired
    private TaskController taskController;

    @Autowired
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Test
    void shouldGetTaskList() {
        // given
        Task task1 = new Task(12L, "Second task", "Second content");
        Task task2 = new Task(13L, "Third task", "Third content");
        List<Task> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);
        when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(taskList.stream()
                .map(t -> new TaskDto(t.getId(), t.getTitle(), t.getContent()))
                .collect(Collectors.toList())
        );
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);
        taskRepository.saveAll(taskList);

        // when
        ResponseEntity<List<TaskDto>> tasksResponse = taskController.getTasks();
        ArrayList<TaskDto> taskDtos = new ArrayList<>(Objects.requireNonNull(tasksResponse.getBody()));

        // then
        assertTrue(taskDtos.containsAll(taskDtoList));

    }

    @Test
    void shouldGetTask() throws TaskNotFoundException {
        // given
        TaskDto taskDto = new TaskDto(11L, "First task", "First content");
        when(taskMapper.mapToTask(taskDto)).thenReturn(new Task(
                taskDto.getId(), taskDto.getTitle(), taskDto.getContent()
        ));
        Task task = taskMapper.mapToTask(taskDto);
        Task savedTask = taskRepository.save(task);

        // when
        ResponseEntity<TaskDto> taskResponse = taskController.getTask(savedTask.getId());

        // then
        assertEquals("First task", Objects.requireNonNull(taskResponse.getBody()).getTitle());
    }

    @Test
    void shouldDeleteTask() {
        // given
        TaskDto taskDto = new TaskDto(15L, "Task to delete", "Content task to delete");
        when(taskMapper.mapToTask(taskDto)).thenReturn(new Task(
                taskDto.getId(), taskDto.getTitle(), taskDto.getContent()
        ));
        Task task = taskMapper.mapToTask(taskDto);
        Task savedTask = taskRepository.save(task);

        // when
        taskController.deleteTask(savedTask.getId());

        // then
        assertEquals(Optional.empty(), taskRepository.findById(savedTask.getId()));
    }

    @Test
    void shouldUpdateTask() {
        // given
        TaskDto taskDto = new TaskDto(16L, "Task to update", "Content task to update");
        when(taskMapper.mapToTask(taskDto)).thenReturn(new Task(
                taskDto.getId(), taskDto.getTitle(), taskDto.getContent()
        ));
        Task task = taskMapper.mapToTask(taskDto);
        taskRepository.save(task);

        // when
        ResponseEntity<TaskDto> taskDtoResponseEntity = taskController.updateTask(taskDto);

        // then
        assertEquals(
                "Task to update",
                Objects.requireNonNull(taskDtoResponseEntity.getBody()).getTitle()
        );
    }

}
