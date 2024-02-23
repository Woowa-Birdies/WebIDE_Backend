package goorm.woowa.webide.project.domain.dto;

import goorm.woowa.webide.project.domain.ProjectLanguage;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class ProjectExecute {
    @NonNull
    private ProjectLanguage language;
    @NonNull
    private String code;

}
