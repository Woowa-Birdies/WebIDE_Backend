package goorm.woowa.webide.project.service;

import com.google.gson.Gson;
import goorm.woowa.webide.member.MemberRepository;
import goorm.woowa.webide.member.data.Member;
import goorm.woowa.webide.project.domain.Project;
import goorm.woowa.webide.project.domain.ProjectLanguage;
import goorm.woowa.webide.project.domain.dto.*;
import goorm.woowa.webide.project.repository.ProjectRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Builder
@Transactional
@RequiredArgsConstructor
public class ProjectQueryService {
    private final ProjectReadService projectReadService;
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;

//    private final EfsUseCase efsService;

    public Long create(ProjectCreate projectCreate) {
        Member member = memberRepository
                .findById(projectCreate.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("해당 Id에 해당하는 Member를 찾을 수 없습니다."));
        Project project = projectRepository
                .save(Project.toEntity(projectCreate, member, UUID.randomUUID().toString()));

        //TODO: EFS
//        String efsAccessPoint = efsService.createEFSAccessPoint(String.valueOf(project.getId()));
//        project.registerEFSAccessPoint(efsAccessPoint);
        return project.getId();
    }

    public Long update(ProjectUpdate projectUpdate) {
        Project project = projectReadService.getById(projectUpdate.getId());
        project.update(projectUpdate);

        return project.getId();
    }

    public Long registerLanguage(Long id, LanguageUpdate languageUpdate) {
        Project project = projectReadService.getById(id);
        project.registerLanguage(languageUpdate.getLanguage());

        return project.getId();
    }

    public Long delete(Long id) {
        Project project = projectReadService.getById(id);
        projectRepository.deleteById(id);
        return project.getId();
    }

    public Long saveCode(Long projectId, ProjectExecute projectExecute) {
        Project project = projectReadService.getById(projectId);
        project.saveCodeAndLanguage(projectExecute.getCode(), projectExecute.getLanguage());

        return project.getId();
    }

    public ProjectResult getProjectResult(Long projectId, ProjectExecute projectExecute) {
        ProjectResult projectResult;
        Project project = projectReadService.getById(projectId);
        Gson gson = new Gson();
        try {
            projectResult = FileExecute.executeFile(projectId, projectExecute.getCode(), projectExecute.getLanguage());
            project.saveResult(projectExecute.getCode(), projectExecute.getLanguage(), gson.toJson(projectResult));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return projectResult;
    }
}
