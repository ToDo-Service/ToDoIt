package com.service.todoit.domain.project.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.service.todoit.domain.project.Project;
import com.service.todoit.domain.user.User;

import java.time.LocalDate;

public record ProjectRequestDto(
        String title,
        String color,
        String description,
        @JsonFormat(pattern = "yyyy.MM.dd")
        LocalDate endDate,
        String category
) {

    public Project toEntity(User user) {
        return new Project(null, user, this.title, this.description, this.color, this.endDate, this.category);
    }
}
