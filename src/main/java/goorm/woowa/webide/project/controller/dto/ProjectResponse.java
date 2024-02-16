package goorm.woowa.webide.project.controller.dto;

import goorm.woowa.webide.problem.controller.dto.ProblemResponse;
import goorm.woowa.webide.problem.domain.Problem;
import goorm.woowa.webide.project.domain.Project;
import goorm.woowa.webide.project.domain.ProjectLanguage;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProjectResponse {
    private Long id;
    private String name;
    private ProjectLanguage language;
    private ProblemResponse problemResponse;
    private LocalDateTime createdAt;

    public static ProjectResponse toResponse(Project project, Problem problem) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .language(project.getLanguage())
                .createdAt(project.getCreatedAt()).build();
    }
}