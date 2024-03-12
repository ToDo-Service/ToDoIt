package com.service.todoit.domain.project.dto;


import com.service.todoit.domain.project.Project;

public record ProjectResponseDto(
        Long id,
        String title,
        String description,
        String color
) {

    public static ProjectResponseDto from(Project project) {
        return new ProjectResponseDto(project.getId(), project.getTitle(), project.getDescription(), project.getColor());
    }
}
