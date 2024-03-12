package com.service.todoit.domain.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.service.todoit.domain.todo.Todo;
import com.service.todoit.domain.todo.TodoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestTodo {
    private String title;
    private String content;
    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDate endDate;
    private Long projectId;
    private String priority;
    private Boolean pushStatus;


    public Todo toEntity(Long userId) {
        return new Todo(userId, this.title, this.content, this.endDate, TodoStatus.INCOMPLETE, this.priority);
    }
}
