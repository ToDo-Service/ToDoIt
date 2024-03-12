package com.service.todoit.domain.todo.dto;

import com.service.todoit.domain.project.dto.ProjectDto;
import com.service.todoit.domain.todo.Todo;
import com.service.todoit.domain.todo.TodoStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodayTodo {
    private Long id;
    private String title;
    private String content;
    private LocalDate endDate;
    private TodoStatus status;
    private String priority;
    private ProjectDto project;

    public static TodayTodo From(Todo todo) {
        return TodayTodo.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .content(todo.getContent())
                .endDate(todo.getEndDate())
                .status(todo.getStatus())
                .priority(todo.getPriority())
                .project((todo.getProject() == null)?null:ProjectDto.From(todo.getProject()))
                .build();
    }
}
