package goorm.woowa.webide.project.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProjectCreate {
    @NotEmpty
    private String name;

    @Builder
    private ProjectCreate(String name) {
        this.name = name;
    }
}
