package goorm.woowa.webide.problem.controller.dto;

import goorm.woowa.webide.problem.domain.Problem;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProblemListResponse {
    private Long id;
    private String title;

    public static ProblemListResponse toResponse(Problem problem) {
        return ProblemListResponse.builder()
                .id(problem.getId())
                .title(problem.getTitle())
                .build();
    }
}
