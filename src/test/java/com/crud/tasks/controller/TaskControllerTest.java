package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskController taskController;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    void shouldGetTaskList() throws Exception {
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
        taskMapper.mapToTaskDtoList(taskList);
        taskRepository.saveAll(taskList);

        // when & then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));

    }

    @Test
    void shouldGetTask() throws Exception {
        // given
        TaskDto taskDto = new TaskDto(11L, "First task", "First content");
        when(taskMapper.mapToTask(taskDto)).thenReturn(new Task(
                taskDto.getId(), taskDto.getTitle(), taskDto.getContent()
        ));
        Task task = taskMapper.mapToTask(taskDto);
        taskRepository.save(task);

        // when & then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/11")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        // given
        TaskDto taskDto = new TaskDto(15L, "Task to delete", "Content task to delete");
        when(taskMapper.mapToTask(taskDto)).thenReturn(new Task(
                taskDto.getId(), taskDto.getTitle(), taskDto.getContent()
        ));
        Task task = taskMapper.mapToTask(taskDto);
        taskRepository.save(task);

        // when & then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/tasks/15")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldUpdateTask() throws Exception {
        // given
        TaskDto taskDto = new TaskDto(16L, "Task to update", "Content task to update");
        when(taskMapper.mapToTask(taskDto)).thenReturn(new Task(
                taskDto.getId(), taskDto.getTitle(), taskDto.getContent()
        ));
        Task task = taskMapper.mapToTask(taskDto);
        taskRepository.save(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        // when & then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldCreateTask() throws Exception {
        // given
        TaskDto taskDto = new TaskDto(19L, "Task to create", "Content task to create");
        when(taskMapper.mapToTask(taskDto)).thenReturn(new Task(
                taskDto.getId(), taskDto.getTitle(), taskDto.getContent()
        ));
        Task task = taskMapper.mapToTask(taskDto);
        taskRepository.save(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        // when & then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

}