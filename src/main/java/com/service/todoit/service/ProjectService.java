package com.service.todoit.service;

import com.service.todoit.common.code.ResponseCode;
import com.service.todoit.common.exception.ApiException;
import com.service.todoit.domain.project.Project;
import com.service.todoit.domain.project.dto.ProjectDto;
import com.service.todoit.domain.project.dto.ProjectRequestDto;
import com.service.todoit.domain.project.dto.ProjectResponseDto;
import com.service.todoit.domain.project.dto.ProjectWithTodoListResponseDto;
import com.service.todoit.domain.todo.TodoStatus;
import com.service.todoit.domain.todo.dto.TodoWithoutProjectResponseDto;
import com.service.todoit.domain.user.User;
import com.service.todoit.repository.ProjectRepository;
import com.service.todoit.repository.TodoRepository;
import com.service.todoit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    @Transactional(readOnly = true)
    public List<ProjectResponseDto> getProjectList(Long id) {
        return projectRepository.findAllByUserId(id)
                .stream().map(ProjectResponseDto::from).toList();
    }

    @Transactional
    public ProjectResponseDto createProject(Authentication authentication, ProjectRequestDto requestDto) {
        Long userId = Long.valueOf((String) authentication.getPrincipal());
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ApiException(ResponseCode.UNAUTHORIZED));

        Project newProject = projectRepository.save(requestDto.toEntity(user));

        return ProjectResponseDto.from(newProject);
    }

    @Transactional
    public ProjectResponseDto updateProject(Authentication authentication, Long projectId, ProjectRequestDto dto) {

        Long userId = Long.valueOf((String) authentication.getPrincipal());
        Project project = projectRepository.findById(projectId)
                .orElseThrow(()-> new ApiException(ResponseCode.BAD_REQUEST));

        if(!project.getUser().getId().equals(userId)){
            throw new ApiException(ResponseCode.UNAUTHORIZED);
        }

        project.update(dto);

        return ProjectResponseDto.from(project);
    }

    @Transactional
    public void deleteProject(Authentication authentication, Long projectId) {
        todoRepository.updateTodo(projectId);

        Long userId = Long.valueOf((String) authentication.getPrincipal());

        Project project = projectRepository.findById(projectId)
                .orElseThrow(()-> new ApiException(ResponseCode.BAD_REQUEST));

        if(!project.getUser().getId().equals(userId)) {
            throw new ApiException(ResponseCode.UNAUTHORIZED);
        }
        projectRepository.delete(project);
    }

    @Transactional(readOnly = true)
    public ProjectWithTodoListResponseDto getProject(Long projectId, Authentication authentication) {
        // 조회 권한이 있는 유저인지 확인
        Long userId = Long.valueOf((String) authentication.getPrincipal());
        Project project = projectRepository.findById(projectId)
                .orElseThrow(()-> new ApiException(ResponseCode.BAD_REQUEST));

        if(!project.getUser().getId().equals(userId)) {
            throw new ApiException(ResponseCode.UNAUTHORIZED);
        }

        List<TodoWithoutProjectResponseDto> todoList = todoRepository.findAllByProjectOrderByEndDateAsc(project)
                .stream().map(TodoWithoutProjectResponseDto::From).toList();

        return new ProjectWithTodoListResponseDto(ProjectResponseDto.from(project), todoList);
    }

}
