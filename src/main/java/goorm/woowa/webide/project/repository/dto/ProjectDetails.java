package goorm.woowa.webide.project.repository.dto;


import goorm.woowa.webide.project.domain.ProjectLanguage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDetails {
    private Long projectId;
    private Long problemId;
    private Long memberId;
    private String memberName;
    private String projectName;
    private ProjectLanguage language;
    private String code;
    private String result;
    private String keyHash;
    private String problem;
    private String title;
    private String candidateName;
    private LocalDateTime birthDate;
}
