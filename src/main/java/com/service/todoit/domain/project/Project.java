package com.service.todoit.domain.project;

import com.service.todoit.domain.BaseEntity;
import com.service.todoit.domain.project.dto.ProjectRequestDto;
import com.service.todoit.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String title;

    private String description;

    private String color;

    private LocalDate endDate;

    private String category;

    public void update(ProjectRequestDto dto) {
        this.title = dto.title();
        this.description = dto.description();
        this.color = dto.color();
    }
}
