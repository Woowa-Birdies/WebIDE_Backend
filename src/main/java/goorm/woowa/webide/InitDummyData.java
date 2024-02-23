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
        Problem problem1 = createProblem("문자열 뒤집기", """
                문제 설명:

                주어진 문자열을 뒤집는 함수를 작성하시오.
                                
                입력 예시:

                input_str = "Hello, world!"\s
                                
                출력 예시:

                output_str = "!dlrow ,olleH"
                                
                설명:
                                
                input_str은 뒤집을 문자열입니다.
                output_str은 뒤집힌 문자열입니다.
                문자열을 뒤집는 방법은 여러 가지가 있습니다.
                """);
        Problem problem2 = createProblem("문자열 퍼즐", """
                문제 설명:

                주어진 문자열과 퍼즐 조각들을 사용하여 원본 문자열을 재구성하는 함수를 작성하시오. 퍼즐 조각들은 원본 문자열의 일부분이며, 순서가 뒤섞여 있을 수 있습니다.

                입력 예시:

                original_str = "abracadabra"
                puzzle_pieces = ["aca", "dab", "ra", "br", "c"]
                                
                출력 예시:

                reconstructed_str = "abracadabra"
                                
                설명:

                original_str은 원본 문자열입니다.
                puzzle_pieces는 퍼즐 조각들의 리스트입니다.
                reconstructed_str은 퍼즐 조각들을 사용하여 재구성된 문자열입니다.""");
        Problem problem3 = createProblem("동전 교환 문제", """
                문제 설명:

                주어진 동전 종류와 금액을 사용하여 최소 개수의 동전으로 금액을 만드는 방법을 찾는 함수를 작성하시오.

                입력 예시:

                coins = [1, 5, 10, 25]
                amount = 11
                                
                출력 예시:

                # 사용된 동전의 개수
                coin_counts = [0, 2, 0, 0]

                # 총 동전 개수
                total_coins = 2
                                
                설명:

                coins는 동전 종류를 나타내는 리스트입니다.
                amount는 만들고자 하는 금액입니다.
                coin_counts는 각 동전 종류의 사용 개수를 저장하는 리스트입니다.
                total_coins는 총 동전의 개수입니다.""");

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

    private Problem createProblem(String title, String problem) {
        return problemRepository.save(
                Problem.builder()
                        .title(title)
                        .problem(problem)
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
