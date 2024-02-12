package goorm.woowa.webide.project.controller;

import goorm.woowa.webide.project.controller.response.ProjectResponse;
import goorm.woowa.webide.project.domain.dto.ProjectCreate;
import goorm.woowa.webide.project.domain.dto.ProjectUpdate;
import goorm.woowa.webide.project.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/projects/{id}")
    public ResponseEntity<ProjectResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ProjectResponse
                .toResponse(projectService.getById(id)));
    }

    @PostMapping("/projects")
    public ResponseEntity<ProjectResponse> create(@RequestBody @Valid ProjectCreate projectCreate) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ProjectResponse
                        .toResponse(projectService.create(projectCreate)));
    }

    @PatchMapping("/projects")
    public ResponseEntity<ProjectResponse> update(@RequestBody @Valid ProjectUpdate projectUpdate) {
        return ResponseEntity.ok(ProjectResponse
                .toResponse(projectService.update(projectUpdate)));
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(projectService.delete(id));
    }

}
