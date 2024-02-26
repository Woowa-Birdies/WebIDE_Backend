package goorm.woowa.webide.project.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
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
