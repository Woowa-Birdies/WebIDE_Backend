package goorm.woowa.webide.candidate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import goorm.woowa.webide.candidate.domain.Candidate;
import goorm.woowa.webide.candidate.domain.CandidateCreate;
import goorm.woowa.webide.candidate.repository.CandidateRepository;
import goorm.woowa.webide.common.TestSecurityConfig;
import goorm.woowa.webide.member.MemberRepository;
import goorm.woowa.webide.member.data.Member;
import goorm.woowa.webide.member.data.MemberRole;
import goorm.woowa.webide.problem.domain.Problem;
import goorm.woowa.webide.problem.repository.ProblemRepository;
import goorm.woowa.webide.project.domain.Project;
import goorm.woowa.webide.project.repository.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
@TestPropertySource("classpath:test-application.yml")
public class CandidateTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private CandidateRepository candidateRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @DisplayName("응시자는 정보를 등록할 수 있다")
    void 응시자는_정보를_등록할_수_있다() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .email("email")
                .pwd("pwd")
                .nickname("nickname")
                .roleList(List.of(MemberRole.USER))
                .build());
        Problem problem = problemRepository.save(Problem.builder()
                .title("title")
                .outputValue("output")
                .inputValue("input")
                .parameter("parameter")
                .build());


        Project project = projectRepository.save(Project.builder()
                .name("CreateTest")
                .member(member)
                .problemId(problem.getId())
                .build());

        CandidateCreate createTest = CandidateCreate.builder()
                .candidateName("candidate")
                .birthDate(LocalDateTime.now())
                .build();

        //when
        //then
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc.perform(post("/candidate/{projectId}", project.getId()).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTest)))
                .andExpect(status().isCreated());
        
        Project project1 = projectRepository.findById(project.getId()).orElseThrow(() -> new NoSuchElementException("그런 Project 없습니다."));
        Candidate candidate = candidateRepository.findById(project1.getCandidateId()).orElseThrow(() -> new NoSuchElementException("그런 응시자 없습니다."));
        assertThat(project1.getCandidateId()).isEqualTo(candidate.getId());
    }
}
