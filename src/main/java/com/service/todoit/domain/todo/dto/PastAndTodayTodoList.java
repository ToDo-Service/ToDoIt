package com.service.todoit.domain.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PastAndTodayTodoList {
    private List<PastTodo> pastTodos;
    private List<TodayTodo> todayTodos;

}
