package goorm.woowa.webide.problem.repository;

import goorm.woowa.webide.problem.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
}
