package goorm.woowa.webide.project.domain.dto;

import goorm.woowa.webide.project.domain.ProjectLanguage;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LanguageUpdate {

    @NotNull
    private ProjectLanguage language;
}
