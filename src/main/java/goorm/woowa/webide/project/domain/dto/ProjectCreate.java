package goorm.woowa.webide.project.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProjectCreate {
    @NotEmpty
    private String name;
    @NotNull
    private Long problemId;
    @NotNull
    private Long memberId;

    @Builder
    public ProjectCreate(String name, Long problemId, Long memberId) {
        this.name = name;
        this.problemId = problemId;
        this.memberId = memberId;
    }
}
