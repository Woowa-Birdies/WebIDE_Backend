package goorm.woowa.webide.project.domain.dto;

import goorm.woowa.webide.project.domain.ProjectLanguage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectExecute {
    @NotEmpty
    private ProjectLanguage language;
    @NotEmpty
    private String code;

}
