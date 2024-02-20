package goorm.woowa.webide.project.repository.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProjectListResponse {
    private Long projectId;
    private String projectName;
    private String candidateName;
    private String problemTitle;
    private String keyHash;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProjectListResponse(Long id, String name, String candidateName, String problemTitle, String keyHash, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.projectId = id;
        this.projectName = name;
        this.candidateName = candidateName;
        this.problemTitle = problemTitle;
        this.keyHash = keyHash;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}