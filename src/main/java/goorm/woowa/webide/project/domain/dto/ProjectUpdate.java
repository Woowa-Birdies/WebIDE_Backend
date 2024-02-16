package goorm.woowa.webide.project.domain.dto;

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

    @Builder
    public ProjectUpdate(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
