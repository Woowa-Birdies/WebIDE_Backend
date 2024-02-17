package goorm.woowa.webide.project.controller;

import goorm.woowa.webide.project.domain.dto.LanguageUpdate;
import goorm.woowa.webide.project.domain.dto.ProjectCreate;
import goorm.woowa.webide.project.domain.dto.ProjectUpdate;
import goorm.woowa.webide.project.repository.dto.ProjectDetails;
import goorm.woowa.webide.project.repository.dto.ProjectListResponse;
import goorm.woowa.webide.project.service.ProjectQueryService;
import goorm.woowa.webide.project.service.ProjectReadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectQueryService projectQueryService;
    private final ProjectReadService projectReadService;

    @GetMapping("/projects/{memberId}")
    public ResponseEntity<List<ProjectListResponse>> getList(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(projectReadService.getListByMemberId(memberId));
    }

    @GetMapping("/ide/{id}")
    public ResponseEntity<ProjectDetails> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(projectReadService.getByIdDetails(id));
    }

    @PostMapping("/projects")
    public ResponseEntity<Long> create(@RequestBody @Valid ProjectCreate projectCreate) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectQueryService.create(projectCreate));
    }

    @PatchMapping("/projects")
    public ResponseEntity<Long> update(@RequestBody @Valid ProjectUpdate projectUpdate) {
        return ResponseEntity.ok(projectQueryService.update(projectUpdate));
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(projectQueryService.delete(id));
    }

    @PatchMapping("/projects/{id}/languages")
    public ResponseEntity<Long> registerLanguage(@PathVariable("id") Long id,
                                                 @RequestBody LanguageUpdate languageUpdate) {
        return ResponseEntity.ok(projectQueryService.registerLanguage(id, languageUpdate));
    }
}
