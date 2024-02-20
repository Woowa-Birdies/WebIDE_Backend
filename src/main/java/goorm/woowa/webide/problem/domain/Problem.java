package goorm.woowa.webide.problem.domain;

import goorm.woowa.webide.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Entity
@Builder
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Problem extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128)
    private String problem;

    @Column(length = 128)
    private String title;

    @Column(columnDefinition = "text")
    private String parameter;

    @Column(columnDefinition = "text")
    private String inputValue;

    @Column(columnDefinition = "text")
    private String outputValue;
}
