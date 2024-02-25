package goorm.woowa.webide.project.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ProjectLanguage {
    CPP,
    JAVA,
    PYTHON;

    @JsonCreator
    public static ProjectLanguage from(String s) {
        return ProjectLanguage.valueOf(s.toUpperCase());
    }
}
