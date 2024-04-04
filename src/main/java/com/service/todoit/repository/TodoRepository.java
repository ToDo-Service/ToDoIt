package com.service.todoit.repository;

import com.service.todoit.domain.project.Project;
import com.service.todoit.domain.todo.Todo;
import com.service.todoit.domain.todo.TodoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    // 과거 완료하지 못한 일정 조회
    List<Todo> findAllByUserIdAndEndDateBeforeAndStatus(Long id, LocalDate endDate, TodoStatus status);

    // 오늘 모든 일정 조회
    List<Todo> findAllByUserIdAndEndDate(Long id, LocalDate endDate);

    // 한주 일정 조회
    List<Todo> findAllByUserIdAndEndDateBetweenOrderByEndDateAsc(Long id, LocalDate startDate, LocalDate endDate);

    List<Todo> findAllByProjectOrderByEndDateAsc(Project project);

    @Modifying
    @Query("update Todo t set t.project = null where t.project.id = :projectId")
    void updateTodo(Long projectId);
}
