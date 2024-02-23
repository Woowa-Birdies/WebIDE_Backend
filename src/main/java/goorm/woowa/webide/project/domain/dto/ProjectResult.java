package goorm.woowa.webide.project.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProjectResult {
    @NotNull
    private String status;
    @NotNull
    private String data;

    @Builder
    public ProjectResult(String status, String data) {
        this.status = status;
        this.data = data;
    }
}
