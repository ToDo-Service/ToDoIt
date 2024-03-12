package com.service.todoit.domain.project.dto;


import com.service.todoit.domain.project.Project;

import java.time.LocalDate;

public record ProjectResponseDto(
        Long id,
        String title,
        String description,
        String color,
        LocalDate endDate,
        String category
) {

    public static ProjectResponseDto from(Project project) {
        return new ProjectResponseDto(project.getId(), project.getTitle(), project.getDescription(), project.getColor(), project.getEndDate(), project.getCategory());
    }
}
