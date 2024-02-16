package goorm.woowa.webide.project.service;

import goorm.woowa.webide.member.MemberRepository;
import goorm.woowa.webide.member.data.Member;
import goorm.woowa.webide.project.domain.Project;
import goorm.woowa.webide.project.domain.dto.ProjectCreate;
import goorm.woowa.webide.project.domain.dto.ProjectUpdate;
import goorm.woowa.webide.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectQueryService {
    private final ProjectReadService projectReadService;
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;

    public Long create(ProjectCreate projectCreate) {
        Member member = memberRepository
                .findById(projectCreate.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("해당 Id에 해당하는 Member를 찾을 수 없습니다."));
        return projectRepository
                .save(Project.toEntity(projectCreate, member)).getId();
    }

    public Long update(ProjectUpdate projectUpdate) {
        Project project = projectReadService.getById(projectUpdate.getId());
        project.update(projectUpdate);

        return project.getId();
    }

    public Long delete(Long id) {
        projectReadService.getById(id);
        projectRepository.deleteById(id);
        return id;
    }
}
