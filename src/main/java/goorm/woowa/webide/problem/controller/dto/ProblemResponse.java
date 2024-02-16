package goorm.woowa.webide.problem.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProblemResponse {
    private Long id;
    private String problem;
    private String title;
    private String parameter;
    private String inputValue;
    private String outputValue;
    
}
