package com.service.todoit.domain.project.dto;

import com.service.todoit.domain.project.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDto {

    private Long id;
    private String description;
    private String color;
    private LocalDate endDate;
    private String category;

    public static ProjectDto From(Project project) {
        return ProjectDto.builder()
                .id(project.getId())
                .description(project.getDescription())
                .color(project.getColor())
                .endDate(project.getEndDate())
                .category(project.getCategory())
                .build();
    }


}
