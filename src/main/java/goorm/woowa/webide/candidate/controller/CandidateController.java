package goorm.woowa.webide.candidate.controller;

import goorm.woowa.webide.candidate.domain.CandidateCreate;
import goorm.woowa.webide.candidate.service.CandidateQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateQueryService candidateQueryService;

    @PostMapping("/candidate/{projectId}")
    public ResponseEntity<Long> create(@PathVariable("projectId") Long projectId,
                                       @RequestBody @Valid CandidateCreate candidateCreate) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(candidateQueryService.create(projectId, candidateCreate));
    }
}
