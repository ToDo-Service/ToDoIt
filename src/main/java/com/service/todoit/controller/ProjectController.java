package com.service.todoit.controller;

import com.service.todoit.common.api.Api;
import com.service.todoit.domain.project.dto.ProjectDto;
import com.service.todoit.domain.project.dto.ProjectRequestDto;
import com.service.todoit.domain.project.dto.ProjectResponseDto;
import com.service.todoit.domain.project.dto.ProjectWithTodoListResponseDto;
import com.service.todoit.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/project")
    public ResponseEntity<?> createProject(
            @RequestBody ProjectRequestDto requestDto,
            Authentication authentication
            ) {
        ProjectResponseDto dto = projectService.createProject(authentication, requestDto);

        return ResponseEntity.ok()
                .body(Api.OK("프로젝트를 생성했습니다.", dto));
    }


    @PatchMapping("/project/{projectId}")
    public ResponseEntity<?> updateProject(
            @PathVariable Long projectId,
            @RequestBody ProjectRequestDto requestDto,
            Authentication authentication
    ) {
        ProjectResponseDto dto = projectService.updateProject(authentication, projectId, requestDto);
        return ResponseEntity.ok()
                .body(Api.OK("프로젝트를 수정했습니다.", dto));
    }

    @DeleteMapping("/project/{projectId}")
    public ResponseEntity<?> deleteProject(
            @PathVariable Long projectId,
            Authentication authentication
    ) {
        projectService.deleteProject(authentication, projectId);
        return ResponseEntity.ok()
                .body(Api.OK("프로젝트를 삭제했습니다.", null));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getProject(
            @PathVariable Long projectId,
            Authentication authentication
    ) {
        ProjectWithTodoListResponseDto response = projectService.getProject(projectId, authentication);
        return ResponseEntity.ok()
                .body(Api.OK("프로젝트에 속한 일정을 조회했습니다.", response));
    }

    @GetMapping("/project")
    public ResponseEntity<?> getProjects(
            Authentication authentication
    ) {
        Long userId = Long.valueOf((String) authentication.getPrincipal());
        List<ProjectDto> response = projectService.getProjectList(userId);
        return ResponseEntity.ok()
                .body(Api.OK("프로젝트 리스트를 조회했습니다.", response));
    }

}
