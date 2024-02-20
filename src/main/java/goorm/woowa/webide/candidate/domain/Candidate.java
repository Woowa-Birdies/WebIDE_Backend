package goorm.woowa.webide.candidate.domain;

import goorm.woowa.webide.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Candidate extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128)
    private String candidateName;

    @Column
    private LocalDateTime birthDate;

    public static Candidate toEntity(CandidateCreate candidateCreate) {
        return Candidate.builder()
                .candidateName(candidateCreate.getCandidateName())
                .birthDate(candidateCreate.getBirthDate())
                .build();
    }
}
