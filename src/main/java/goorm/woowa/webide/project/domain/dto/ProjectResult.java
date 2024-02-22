package goorm.woowa.webide.project.domain.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
public class ProjectResult {
    @NonNull
    private String status;
    @NonNull
    private String data;

    @Builder
    public ProjectResult(String status, String data) {
        this.status = status;
        this.data = data;
    }
}
