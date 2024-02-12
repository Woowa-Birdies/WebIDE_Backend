package goorm.woowa.webide.project.domain.dto;

import goorm.woowa.webide.project.domain.ProjectLanguage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProjectUpdate {
    @NotNull
    private Long id;
    @NotEmpty
    private String name;
    @NotNull
    private ProjectLanguage language;

    @Builder
    public ProjectUpdate(Long id, String name, ProjectLanguage language) {
        this.id = id;
        this.name = name;
        this.language = language;
    }
}
