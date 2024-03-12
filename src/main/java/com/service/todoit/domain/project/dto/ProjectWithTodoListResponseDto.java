package com.service.todoit.domain.project.dto;

import com.service.todoit.domain.todo.dto.TodoWithoutProjectResponseDto;

import java.util.List;

public record ProjectWithTodoListResponseDto(
        ProjectResponseDto projectInfo,
        List<TodoWithoutProjectResponseDto> todoList
) {
}
