package com.service.todoit.controller;

import com.service.todoit.common.api.Api;
import com.service.todoit.domain.todo.TodoStatus;
import com.service.todoit.domain.todo.dto.PastAndTodayTodoList;
import com.service.todoit.domain.todo.dto.RequestTodo;
import com.service.todoit.domain.todo.dto.TodoResponse;
import com.service.todoit.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TodoController {
    private final TodoService todoService;

    // 일정 생성
    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(
        @RequestBody RequestTodo todo,
        Authentication authentication
    ) {
        System.out.println(todo);
        TodoResponse todoEntity = todoService.saveTodo(todo, authentication);

        return ResponseEntity.ok()
                .body(Api.OK("일정이 성공적으로 생성되었습니다.", todoEntity));
    }

    @PatchMapping("/todos/{todoId}")
    public ResponseEntity<?> updateTodo(
            @RequestBody RequestTodo todo,
            @PathVariable Long todoId
    ){
        TodoResponse response = todoService.updateTodo(todo, todoId);

        return ResponseEntity.ok()
                .body(Api.OK("일정이 성공적으로 수정되었습니다.", response));
    }

    @PatchMapping("/todos/status/{todoId}")
    public ResponseEntity<?> changeTodoStatus(
            @PathVariable Long todoId
    ) {
        TodoStatus status =todoService.completeTodo(todoId);

        return ResponseEntity.ok()
                .body(Api.OK("일정 상태가 성공적으로 수정되었습니다.", status.getStatus()));

    }

    @DeleteMapping("/todos/{todoId}")
    public ResponseEntity<?> deleteTodo(
            @PathVariable Long todoId
    ) {
        todoService.deleteTodo(todoId);
        return ResponseEntity.ok()
                .body(Api.OK("일정이 성공적으로 삭제되었습니다.", null));
    }
    @GetMapping("/todos/today")
    public ResponseEntity<?> getTodayTodos(Authentication authentication) {
        Long userId = Long.valueOf((String) authentication.getPrincipal());
        PastAndTodayTodoList response = todoService.getTodayTodoList(userId);

        return ResponseEntity.ok()
                .body(Api.OK("오늘 일정이 성공적으로 조회되었습니다.", response));
    }

    @GetMapping("/todos/week")
    public ResponseEntity<?> getWeekTodos(
            Authentication authentication,
            @RequestParam LocalDate date
    ) {
        List<TodoResponse> todoList = todoService.getWeekTodo(authentication, date);
        return ResponseEntity.ok()
                .body(Api.OK("주간 일정이 성공적으로 조회되었습니다.", todoList));
    }

    @GetMapping("/todos/month")
    public ResponseEntity<?> getMonthTodos(
            Authentication authentication,
            @RequestParam Integer year,
            @RequestParam Integer month
    ) {
        List<TodoResponse> todoList = todoService.getMonthTodo(authentication, year, month);
        return ResponseEntity.ok()
                .body(Api.OK("월간 일정이 성공적으로 조회되었습니다.", todoList));

    }


}
