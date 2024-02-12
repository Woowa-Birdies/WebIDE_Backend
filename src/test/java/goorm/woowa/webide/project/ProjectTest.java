package goorm.woowa.webide.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import goorm.woowa.webide.project.domain.Project;
import goorm.woowa.webide.project.domain.ProjectLanguage;
import goorm.woowa.webide.project.domain.dto.ProjectCreate;
import goorm.woowa.webide.project.domain.dto.ProjectUpdate;
import goorm.woowa.webide.project.service.ProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:test-application.yml")
class ProjectTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProjectService projectService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("사용자는 프로젝트를 상세조회할 수 있다")
    @WithMockUser(username = "test", roles = "USER")
    void 사용자는_프로젝트를_상세조회할_수_있다() throws Exception {
        //given
        Project createdProject = projectService.create(ProjectCreate.builder()
                .name("CreateTest")
                .build());
        //when
        //then
        mockMvc.perform(get("/projects/{id}", createdProject.getId()).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("CreateTest"));
    }


    @Test
    @DisplayName("사용자는 프로젝트를 만들 수 있다")
    @WithMockUser(username = "test", roles = "USER")
    void 사용자는_프로젝트를_만들_수_있다() throws Exception {
        //given
        ProjectCreate createTest = ProjectCreate.builder()
                .name("CreateTest")
                .build();

        //when
        //then
        mockMvc.perform(post("/projects").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("CreateTest"));
    }

    @Test
    @DisplayName("사용자는 프로젝트를 수정할 수 있다")
    @WithMockUser(username = "test", roles = "USER")
    void 사용자는_프로젝트를_수정할_수_있다() throws Exception {
        //given
        Project createdProject = projectService.create(ProjectCreate.builder()
                .name("CreateTest")
                .build());

        ProjectUpdate updateTest = ProjectUpdate.builder()
                .id(createdProject.getId())
                .name("UpdateTest")
                .language(ProjectLanguage.JAVA)
                .build();
        //when
        //then
        mockMvc.perform(patch("/projects").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateTest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("UpdateTest"))
                .andExpect(jsonPath("$.language").value("JAVA"));
    }

    @Test
    @DisplayName("사용자는 프로젝트를 삭제할 수 있다")
    @WithMockUser(username = "test", roles = "USER")
    void 사용자는_프로젝트를_삭제할_수_있다() throws Exception {
        //given
        Project createdProject = projectService.create(ProjectCreate.builder()
                .name("CreateTest")
                .build());
        //when
        //then
        mockMvc.perform(delete("/projects/{id}", createdProject.getId()).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(createdProject.getId().toString()));
    }
}
