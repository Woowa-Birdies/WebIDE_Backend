package goorm.woowa.webide.project.repository.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProjectListResponse {
    private Long id;
    private String name;
    private String candidateName;
    private String problemTitle;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProjectListResponse(Long id, String name, String candidateName, String problemTitle, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.candidateName = candidateName;
        this.problemTitle = problemTitle;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}