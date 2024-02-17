package goorm.woowa.webide.project.service;

import goorm.woowa.webide.project.domain.Project;
import goorm.woowa.webide.project.repository.ProjectRepository;
import goorm.woowa.webide.project.repository.dto.ProjectDetails;
import goorm.woowa.webide.project.repository.dto.ProjectListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectReadService {
    private final ProjectRepository projectRepository;


    // TODO : User 연관관계 생기면 User에 따른 프로젝트 리스트 반환

    // get: Non-Optional / find : Optional
    public Project getById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 Id에 Project를 찾을 수 없습니다."));
    }

    public ProjectDetails getByIdDetails(Long id) {
        return projectRepository.findProjectDetailsById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 Id에 Project를 찾을 수 없습니다."));
    }

    public List<ProjectListResponse> getListByMemberId(Long memberId) {
        return projectRepository.findProjectListByMemberId(memberId);
    }
}
