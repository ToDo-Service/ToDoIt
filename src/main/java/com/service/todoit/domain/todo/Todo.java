package com.service.todoit.domain.todo;

import com.service.todoit.domain.BaseEntity;
import com.service.todoit.domain.project.Project;
import com.service.todoit.domain.todo.dto.RequestTodo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
public class Todo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private Long userId;

    @Column(nullable = false) @Setter
    private String title;
    @Setter
    private String content;
    @Setter
    private LocalDate endDate;
    @Setter
    @Enumerated(EnumType.STRING)
    private TodoStatus status;
    @Setter
    private String priority;

    // 프로젝트 추가해야함.
    @ManyToOne @Setter
    private Project project;

    public Todo(Long userId, String title, String content, LocalDate endDate, TodoStatus status, String priority) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.endDate = endDate;
        this.status = status;
        this.priority = priority;
    }

    public void update(RequestTodo todo){
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.endDate = todo.getEndDate();
        this.priority = todo.getPriority();
    }
}
