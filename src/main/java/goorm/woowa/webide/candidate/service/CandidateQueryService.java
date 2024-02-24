package goorm.woowa.webide.candidate.service;

import goorm.woowa.webide.candidate.domain.Candidate;
import goorm.woowa.webide.candidate.domain.CandidateCreate;
import goorm.woowa.webide.candidate.repository.CandidateRepository;
import goorm.woowa.webide.project.domain.Project;
import goorm.woowa.webide.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class CandidateQueryService {

    private final CandidateRepository candidateRepository;
    private final ProjectRepository projectRepository;

    public Long create(Long projectId, CandidateCreate candidateCreate) {
        Candidate candidate = candidateRepository.save(Candidate.toEntity(candidateCreate));
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("해당 Id에 Project를 찾을 수 없습니다."));
        project.registerLanguage(candidateCreate.getLanguage());
        project.registerCandidate(candidate);
        return candidate.getId();
    }
}
