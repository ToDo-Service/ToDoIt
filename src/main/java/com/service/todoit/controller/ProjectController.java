package com.service.todoit.controller;

import com.service.todoit.common.api.Api;
import com.service.todoit.domain.project.dto.ProjectRequestDto;
import com.service.todoit.domain.project.dto.ProjectResponseDto;
import com.service.todoit.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

}
