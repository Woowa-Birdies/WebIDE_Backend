package goorm.woowa.webide.problem.controller;

import goorm.woowa.webide.problem.controller.dto.ProblemListResponse;
import goorm.woowa.webide.problem.service.ProblemReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemReadService problemReadService;

    @GetMapping("/problems")
    public ResponseEntity<List<ProblemListResponse>> getTitleList() {
        return ResponseEntity.ok(problemReadService.getTitleList());
    }
}
