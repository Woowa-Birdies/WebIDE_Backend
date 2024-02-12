package goorm.woowa.webide.project.controller.response;

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
    private String ecsInfo;
    private LocalDateTime createdAt;


    public static ProjectResponse toResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .language(project.getLanguage())
                .createdAt(project.getCreatedAt()).build();
    }
}
