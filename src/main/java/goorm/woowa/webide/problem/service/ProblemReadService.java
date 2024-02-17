package goorm.woowa.webide.problem.service;

import goorm.woowa.webide.problem.controller.dto.ProblemListResponse;
import goorm.woowa.webide.problem.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemReadService {

    private final ProblemRepository problemRepository;

    public List<ProblemListResponse> getTitleList() {
        return problemRepository.findAll().stream()
                .map(ProblemListResponse::toResponse)
                .toList();
    }
    
}
