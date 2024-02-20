package goorm.woowa.webide.candidate.repository;

import goorm.woowa.webide.candidate.domain.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
}
