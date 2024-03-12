package com.service.todoit.domain.todo.dto;


import com.service.todoit.domain.todo.Todo;
import com.service.todoit.domain.todo.TodoStatus;

import java.time.LocalDate;

public record TodoWithoutProjectResponseDto(
        Long id,
        String title,
        String content,
        LocalDate endDate,
        TodoStatus status,
        String priority
) {

    public static TodoWithoutProjectResponseDto From(Todo todo) {
        return new TodoWithoutProjectResponseDto(
                todo.getId(),
                todo.getTitle(),
                todo.getContent(),
                todo.getEndDate(),
                todo.getStatus(),
                todo.getPriority()
        );
    }
}
