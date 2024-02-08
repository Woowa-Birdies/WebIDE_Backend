package goorm.woowa.webide.project.service;

import goorm.woowa.webide.project.domain.Project;
import goorm.woowa.webide.project.domain.dto.ProjectCreate;
import goorm.woowa.webide.project.domain.dto.ProjectUpdate;
import goorm.woowa.webide.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    // TODO : User 연관관계 생기면 User에 따른 프로젝트 리스트 반환

    // get: Non-Optional / find : Optional
    public Project getById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 Id에 Project를 찾을 수 없습니다."));
    }

    @Transactional
    public Project create(ProjectCreate projectCreate) {
        return projectRepository
                .save(Project.toEntity(projectCreate));
    }

    @Transactional
    public Project update(ProjectUpdate projectUpdate) {
        Project project = getById(projectUpdate.getId());
        project.update(projectUpdate);

        return project;
    }

    @Transactional
    public Long delete(Long id) {
        getById(id);
        projectRepository.deleteById(id);
        return id;
    }
}
