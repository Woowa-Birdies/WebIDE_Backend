package goorm.woowa.webide.project.repository;

import goorm.woowa.webide.project.domain.Project;
import goorm.woowa.webide.project.repository.dto.ProjectDetails;
import goorm.woowa.webide.project.repository.dto.ProjectListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT new goorm.woowa.webide.project.repository.dto.ProjectDetails(p.id, pb.id, m.id, m.nickname, " +
            "p.name, p.language, p.code, p.keyHash, pb.problem, pb.title, pb.parameter, pb.inputValue, " +
            "pb.outputValue, c.candidateName) " +
            "FROM Project p " +
            "JOIN Member m ON p.member = m " +
            "JOIN Problem pb ON p.problemId = pb.id " +
            "LEFT JOIN Candidate c ON p.candidateId = c.id " +
            "WHERE p.id = :projectId")
    Optional<ProjectDetails> findProjectDetailsById(@Param("projectId") Long projectId);


    @Query("SELECT new goorm.woowa.webide.project.repository.dto.ProjectListResponse(p.id, p.name, c.candidateName, " +
            "pb.title, p.keyHash, p.createdAt, p.updatedAt) " +
            "FROM Project p " +
            "JOIN Member m ON p.member = m " +
            "JOIN Problem pb ON p.problemId = pb.id " +
            "LEFT JOIN Candidate c ON p.candidateId = c.id " +
            "WHERE m.id = :memberId")
    List<ProjectListResponse> findProjectListByMemberId(@Param("memberId") Long memberId);
}
