package goorm.woowa.webide;

import goorm.woowa.webide.candidate.domain.Candidate;
import goorm.woowa.webide.candidate.repository.CandidateRepository;
import goorm.woowa.webide.member.MemberRepository;
import goorm.woowa.webide.member.data.Member;
import goorm.woowa.webide.member.data.MemberRole;
import goorm.woowa.webide.problem.domain.Problem;
import goorm.woowa.webide.problem.repository.ProblemRepository;
import goorm.woowa.webide.project.domain.Project;
import goorm.woowa.webide.project.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDummyData {

    private final ProblemRepository problemRepository;
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final CandidateRepository candidateRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {
        // 더미 데이터 저장 로직
        // Member 생성
        Member member1 = createMember("email1", "pwd1", "nickname1");
        Member member2 = createMember("email2", "pwd2", "nickname2");
        Member member3 = createMember("email3", "pwd3", "nickname3");

        // Problem 생성
        Problem problem1 = createProblem("problem title1", "problem1", "input1", "output1", "parameter1");
        Problem problem2 = createProblem("problem title2", "problem2", "input2", "output2", "parameter2");
        Problem problem3 = createProblem("problem title3", "problem3", "input3", "output3", "parameter3");

        // Candidate 생성
        Candidate candidate1 = createCandidate("name1");
        Candidate candidate2 = createCandidate("name2");
        Candidate candidate3 = createCandidate("name3");

        // Project 생성
        createProject("code1", member1, "hash1", "project1", candidate1.getId(), problem1.getId());
        createProject("code2", member2, "hash2", "project2", candidate2.getId(), problem2.getId());
        createProject("code3", member3, "hash3", "project3", candidate3.getId(), problem3.getId());
    }

    private Member createMember(String email, String pwd, String nickname) {
        return memberRepository.save(
                Member.builder()
                        .email(email)
                        .pwd(pwd)
                        .nickname(nickname)
                        .roleList(List.of(MemberRole.USER))
                        .build()
        );
    }

    private Project createProject(String code, Member member, String hash, String project, Long candidateId, Long problemId) {
        return projectRepository.save(
                Project.builder()
                        .code(code)
                        .member(member)
                        .keyHash(hash)
                        .name(project)
                        .candidateId(candidateId)
                        .problemId(problemId)
                        .build()
        );
    }

    private Problem createProblem(String title, String problem, String input, String output, String parameter) {
        return problemRepository.save(
                Problem.builder()
                        .title(title)
                        .problem(problem)
                        .inputValue(input)
                        .outputValue(output)
                        .parameter(parameter)
                        .build()
        );
    }

    private Candidate createCandidate(String name) {
        return candidateRepository.save(
                Candidate.builder()
                        .candidateName(name)
                        .birthDate(LocalDateTime.now())
                        .build()
        );
    }
}
