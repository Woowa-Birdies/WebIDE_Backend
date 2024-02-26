package goorm.woowa.webide.project.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProjectResult {
    @NotNull
    private java.lang.String status;
    @NotNull
    private java.lang.String data;

    @Builder
    public ProjectResult(java.lang.String status, java.lang.String data) {
        this.status = status;
        this.data = data;
    }
}
