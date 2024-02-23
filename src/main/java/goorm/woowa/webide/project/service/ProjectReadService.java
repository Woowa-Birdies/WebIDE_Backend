package goorm.woowa.webide.project.service;

import goorm.woowa.webide.project.domain.Project;
import goorm.woowa.webide.project.domain.ProjectLanguage;
import goorm.woowa.webide.project.domain.dto.ProjectExecute;
import goorm.woowa.webide.project.domain.dto.ProjectResult;
import goorm.woowa.webide.project.repository.ProjectRepository;
import goorm.woowa.webide.project.repository.dto.ProjectDetails;
import goorm.woowa.webide.project.repository.dto.ProjectListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectReadService {
    
    private final ProjectRepository projectRepository;


    // get: Non-Optional / find : Optional
    public Project getById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 Id에 Project를 찾을 수 없습니다."));
    }

    public ProjectDetails getByIdDetails(Long memberId, Long projectId, String hash) {
        ProjectDetails projectDetails = projectRepository.findProjectDetailsById(projectId)
                .orElseThrow(() -> new NoSuchElementException("해당 Id에 Project를 찾을 수 없습니다."));

        // 응시자 체크
        if (hash != null && !hash.equals(projectDetails.getKeyHash())) {
            throw new IllegalStateException("프로젝트 응시자가 아닙니다.");
        }

        // 프로젝트 생성자 체크
        if (!projectDetails.getMemberId().equals(memberId))
            throw new IllegalStateException("프로젝트 소유자가 아닙니다.");
        return projectDetails;
    }

    public List<ProjectListResponse> getListByMemberId(Long memberId) {
        return projectRepository.findProjectListByMemberId(memberId);
    }

    public ProjectResult getProjectResult(Long projectId, ProjectExecute projectExecute) {
        ProjectResult pr;
        try {
            pr = FileExecute.executeFile(projectExecute.getCode(), projectExecute.getLanguage());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return pr;
    }
}
