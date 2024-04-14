package com.service.todoit.service;

import com.service.todoit.common.code.ResponseCode;
import com.service.todoit.common.exception.ApiException;
import com.service.todoit.domain.project.Project;
import com.service.todoit.domain.todo.Todo;
import com.service.todoit.domain.todo.TodoStatus;
import com.service.todoit.domain.todo.dto.*;
import com.service.todoit.repository.ProjectRepository;
import com.service.todoit.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final ProjectRepository projectRepository;

    @Transactional(readOnly = true)
    public PastAndTodayTodoList getTodayTodoList(Long id) {
        // 과거에 완료하지 못한 일정들 조회
        List<PastTodo> pastList = todoRepository.findAllByUserIdAndEndDateBeforeAndStatus(id, LocalDate.now(), TodoStatus.INCOMPLETE)
                .stream().map(PastTodo::From).toList();
        // 오늘 일정 조회
        List<TodayTodo> todayList = todoRepository.findAllByUserIdAndEndDate(id, LocalDate.now())
                .stream().map(TodayTodo::From).toList();

        return new PastAndTodayTodoList(pastList, todayList);
    }

    @Transactional
    public TodoResponse saveTodo(RequestTodo todo, Authentication authentication) {
        Long userId = Long.valueOf((String)authentication.getPrincipal());
        Todo todoEntity = todo.toEntity(userId);
        
        // Project 저장
        if(todo.getProjectId() != null) {
            Project project = projectRepository.findById(todo.getProjectId())
                    .orElseThrow(()->new ApiException(ResponseCode.BAD_REQUEST));
            todoEntity.setProject(project);
        }
        Todo newTodo = todoRepository.save(todoEntity);

        return TodoResponse.From(newTodo);
    }

    @Transactional
    public TodoResponse updateTodo(RequestTodo todo, Long todoId) {
        Todo todoEntity = todoRepository.findById(todoId)
                .orElseThrow(() -> new ApiException(ResponseCode.BAD_REQUEST));

        todoEntity.update(todo);

        if(todo.getProjectId() != null) {
            Project project = projectRepository.findById(todo.getProjectId())
                    .orElseThrow(()->new ApiException(ResponseCode.BAD_REQUEST));
            todoEntity.setProject(project);
        }
        return TodoResponse.From(todoEntity);
    }

    @Transactional
    public void deleteTodo(Long todoId) {
        todoRepository.deleteById(todoId);
    }

    @Transactional(readOnly = true)
    public List<TodoResponse> getDayTodo(Authentication authentication, LocalDate date) {
        Long userId = Long.valueOf((String) authentication.getPrincipal());

        return todoRepository.findAllByUserIdAndEndDate(userId, date)
                .stream().map(TodoResponse::From).toList();
    }

    @Transactional(readOnly = true)
    public List<TodoResponse> getWeekTodo(Authentication authentication, LocalDate date) {
        Long userId = Long.valueOf((String) authentication.getPrincipal());
        LocalDate endDate = date.plusDays(6);

        return todoRepository.findAllByUserIdAndEndDateBetweenOrderByEndDateAsc(userId, date, endDate)
                .stream().map(TodoResponse::From).toList();
    }

    @Transactional(readOnly = true)
    public List<TodoResponse> getMonthTodo(Authentication authentication, Integer year, Integer month) {
        Long userId = Long.valueOf((String) authentication.getPrincipal());
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        return todoRepository.findAllByUserIdAndEndDateBetweenOrderByEndDateAsc(userId, startDate, endDate)
                .stream().map(TodoResponse::From).toList();

    }

    @Transactional
    public TodoStatus completeTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(()-> new ApiException(ResponseCode.BAD_REQUEST));

        if(todo.getStatus() == TodoStatus.INCOMPLETE){
            todo.setStatus(TodoStatus.COMPLETE);
        } else {
            todo.setStatus(TodoStatus.INCOMPLETE);
        }

        return todo.getStatus();
    }

    public List<TodoResponse> getYearTodo(Authentication authentication, Integer year) {
        Long userId = Long.valueOf((String) authentication.getPrincipal());
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year,12,31);
        return todoRepository.findAllByUserIdAndEndDateBetweenOrderByEndDateAsc(userId, startDate, endDate)
                .stream().map(TodoResponse::From).toList();
    }
}
