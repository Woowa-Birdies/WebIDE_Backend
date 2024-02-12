package goorm.woowa.webide.project.repository;

import goorm.woowa.webide.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
