package goorm.woowa.webide.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import goorm.woowa.webide.common.TestSecurityConfig;
import goorm.woowa.webide.member.MemberRepository;
import goorm.woowa.webide.member.data.Member;
import goorm.woowa.webide.member.data.MemberRole;
import goorm.woowa.webide.problem.domain.Problem;
import goorm.woowa.webide.problem.repository.ProblemRepository;
import goorm.woowa.webide.project.domain.ProjectLanguage;
import goorm.woowa.webide.project.domain.dto.ProjectCreate;
import goorm.woowa.webide.project.domain.dto.ProjectExecute;
import goorm.woowa.webide.project.domain.dto.ProjectResult;
import goorm.woowa.webide.project.domain.dto.ProjectUpdate;
import goorm.woowa.webide.project.repository.ProjectRepository;
import goorm.woowa.webide.project.service.FileExecute;
import goorm.woowa.webide.project.service.ProjectQueryService;
import goorm.woowa.webide.project.service.ProjectReadService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static goorm.woowa.webide.project.domain.ProjectLanguage.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
@TestPropertySource("classpath:test-application.yml")
class ProjectTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectReadService projectReadService;
    @Autowired
    private ProjectQueryService projectQueryService;

    private final ObjectMapper objectMapper = new ObjectMapper();


//    @BeforeEach
//    void setUp() {
//        projectQueryService = ProjectQueryService.builder()
//                .efsService(new TestEfsService())
//                .projectRepository(projectRepository)
//                .memberRepository(memberRepository)
//                .projectReadService(projectReadService)
//                .build();
//    }

    @Test
    @DisplayName("사용자는 프로젝트를 상세조회할 수 있다")
    @WithMockUser(username = "test", roles = "USER")
    void 사용자는_프로젝트를_상세조회할_수_있다() throws Exception {
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

        Long projectId = projectQueryService.create(ProjectCreate.builder()
                .name("CreateTest")
                .memberId(member.getId())
                .problemId(problem.getId())
                .build());
        //when
        //then
        mockMvc.perform(get("/ide/{memberId}/{projectId}", member.getId(), projectId).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectId").isNumber())
                .andExpect(jsonPath("$.projectName").value("CreateTest"))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.outputValue").value("output"))
                .andExpect(jsonPath("$.inputValue").value("input"))
                .andExpect(jsonPath("$.memberName").value("nickname"))
                .andExpect(jsonPath("$.parameter").value("parameter"));
    }


    @Test
    @DisplayName("사용자는 프로젝트 리스트를 조회할 수 있다")
    void 사용자는_프로젝트_리스트를_조회할_수_있다() throws Exception {
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


        Long projectId1 = projectQueryService.create(ProjectCreate.builder()
                .name("CreateTest1")
                .memberId(member.getId())
                .problemId(problem.getId())
                .build());
        Long projectId2 = projectQueryService.create(ProjectCreate.builder()
                .name("CreateTest2")
                .memberId(member.getId())
                .problemId(problem.getId())
                .build());
        //when
        //then
        mockMvc.perform(get("/projects/{memberId}", member.getId()).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].projectId").isNumber())
                .andExpect(jsonPath("$[0].projectName").value("CreateTest1"))
                .andExpect(jsonPath("$[1].projectName").value("CreateTest2"))
                .andExpect(jsonPath("$[0].candidateName").isEmpty())
                .andExpect(jsonPath("$[0].problemTitle").value("title"))
                .andExpect(jsonPath("$[0].keyHash").isNotEmpty());
    }

    @Test
    @DisplayName("사용자는 프로젝트를 만들 수 있다")
    @WithMockUser(username = "test", roles = "USER")
    void 사용자는_프로젝트를_만들_수_있다() throws Exception {
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

        ProjectCreate createTest = ProjectCreate.builder()
                .name("CreateTest")
                .memberId(member.getId())
                .problemId(problem.getId())
                .build();

        //when
        //then
        mockMvc.perform(post("/projects").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTest)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("사용자는 프로젝트를 수정할 수 있다")
    @WithMockUser(username = "test", roles = "USER")
    void 사용자는_프로젝트를_수정할_수_있다() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .email("email")
                .pwd("pwd")
                .nickname("nickname")
                .build());
        Problem problem = problemRepository.save(Problem.builder()
                .title("title")
                .outputValue("output")
                .inputValue("input")
                .parameter("parameter")
                .build());

        Long projectId = projectQueryService.create(ProjectCreate.builder()
                .name("CreateTest")
                .memberId(member.getId())
                .problemId(problem.getId())
                .build());

        ProjectUpdate updateTest = ProjectUpdate.builder()
                .id(projectId)
                .name("UpdateTest")
                .build();
        //when
        //then
        mockMvc.perform(patch("/projects").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateTest)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("사용자는 프로젝트를 삭제할 수 있다")
    @WithMockUser(username = "test", roles = "USER")
    void 사용자는_프로젝트를_삭제할_수_있다() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .email("email")
                .pwd("pwd")
                .nickname("nickname")
                .build());
        Problem problem = problemRepository.save(Problem.builder()
                .title("title")
                .outputValue("output")
                .inputValue("input")
                .parameter("parameter")
                .build());

        Long projectId = projectQueryService.create(ProjectCreate.builder()
                .name("CreateTest")
                .memberId(member.getId())
                .problemId(problem.getId())
                .build());
        //when
        //then
        mockMvc.perform(delete("/projects/{id}", projectId).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(projectId.toString()));
    }


    @Test
    @DisplayName("사용자는 프로젝트를 실행시킬 수 있다.")
    @WithMockUser(username = "test", roles = "USER")
    void 사용자는_프로젝트를_실행시킬_수_있다() throws Exception {
        // given
        Member member = memberRepository.save(Member.builder()
                .email("email")
                .pwd("pwd")
                .nickname("nickname")
                .build());
        Problem problem = problemRepository.save(Problem.builder()
                .title("title")
                .outputValue("output")
                .inputValue("input")
                .parameter("parameter")
                .build());

        Long projectId = projectQueryService.create(ProjectCreate.builder()
                .name("CreateTest")
                .memberId(member.getId())
                .problemId(problem.getId())
                .build());

        String pythonCode = "print('hello')\\nprint('hello')";
        String javaCode =
                "public class Test {\\n" +
                "   public static void main(String[] args) {" +
                "       System.out.println(\"hello\");" +
                "   }" +
                "\n}";
        String cppCode =
                "#include <iostream>\n" +
                        "\n" +
                        "using namespace std;\n" +
                        "\n" +
                        "int main(int argc, char const *argv[])\n" +
                        "{\n" +
                        "    cout << \"input numbers\" << endl;\n" +
//                        "    int x;\n" +
//                        "    cin >> x;\n" +
//                        "    cout << \"hello : \" << x << endl;\n" +
                        "    return 0;\n" +
                        "}";

        // when
//        FileExecute.executeFile(cppCode, CPP);

        String result = "{\"status\":\"executed\",\"data\":\"{\\\"1\\\":\\\"hello\\\",\\\"2\\\":\\\"hello\\\"}\"}";
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "hello");
        map.put(2, "hello");

        Gson gson = new Gson();
        String json = gson.toJson(map);

        ProjectResult expectedResult = ProjectResult.builder()
                .status("executed")
                .data(json)
                .build();

        String input = "{\"language\": \"python\", \"code\": \"" + pythonCode +"\"}";

        // then
        mockMvc.perform(post("/ide/{id}/result", projectId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        //json 형식으로 데이터를 보낸다고 명시
                        .content(input)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(result));
    }


    @Test
    @DisplayName("사용자는 프로젝트를 저장할 수 있다.")
    @WithMockUser(username = "test", roles = "USER")
    void 사용자는_프로젝트를_저장할_수_있다() throws Exception {
        // given
        Member member = memberRepository.save(Member.builder()
                .email("email")
                .pwd("pwd")
                .nickname("nickname")
                .build());
        Problem problem = problemRepository.save(Problem.builder()
                .title("title")
                .outputValue("output")
                .inputValue("input")
                .parameter("parameter")
                .build());

        Long projectId = projectQueryService.create(ProjectCreate.builder()
                .name("CreateTest")
                .memberId(member.getId())
                .problemId(problem.getId())
                .build());

        String pythonCode = "print('hello')";
        String javaCode =
                "public class Test {\n" +
                "   public static void main(String[] args) {" +
                "       System.out.println(\"hello\");" +
                "   }" +
                "\n}";
        String cppCode =
                "#include <iostream>\n" +
                        "\n" +
                        "using namespace std;\n" +
                        "\n" +
                        "int main(int argc, char const *argv[])\n" +
                        "{\n" +
                        "    cout << \"input numbers\" << endl;\n" +
//                        "    int x;\n" +
//                        "    cin >> x;\n" +
//                        "    cout << \"hello : \" << x << endl;\n" +
                        "    return 0;\n" +
                        "}";

        ProjectExecute projectExecute = ProjectExecute.builder()
                .language(PYTHON)
                .code(pythonCode)
                .build();

        // when
        String input = "{\"language\": \"python\", \"code\": \"" + pythonCode +"\"}";

        // then
        mockMvc.perform(patch("/ide/{id}/save", projectId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        //json 형식으로 데이터를 보낸다고 명시
                        .content(input)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(projectId.toString()));
    }
}
