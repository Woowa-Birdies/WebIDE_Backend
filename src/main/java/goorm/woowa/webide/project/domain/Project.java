package goorm.woowa.webide.project.domain;


import goorm.woowa.webide.candidate.domain.Candidate;
import goorm.woowa.webide.common.domain.BaseTimeEntity;
import goorm.woowa.webide.member.data.Member;
import goorm.woowa.webide.project.domain.dto.ProjectCreate;
import goorm.woowa.webide.project.domain.dto.ProjectUpdate;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Entity
@Builder
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    Member member;

    @Column
    private Long problemId;

    @Column
    private Long candidateId;

    @Column(length = 128)
    private String name;

    @Enumerated(EnumType.STRING)
    private ProjectLanguage language;

    @Column(length = 128)
    private String ecsInfo;

    @Column(columnDefinition = "text")
    private String code;

    @Column(columnDefinition = "text")
    private String keyHash;

    public Project(String name, Long problemId, Member member, String keyHash) {
        this.name = name;
        this.problemId = problemId;
        this.member = member;
        this.keyHash = keyHash;
    }

    public static Project toEntity(ProjectCreate projectCreate, Member member, String keyHash) {
        return new Project(projectCreate.getName(),
                projectCreate.getProblemId(),
                member,
                keyHash
        );
    }

    public void update(ProjectUpdate projectUpdate) {
        this.name = projectUpdate.getName();
    }

    public void registerCandidate(Candidate candidate) {
        this.candidateId = candidate.getId();
    }

    public void registerLanguage(ProjectLanguage language) {
        this.language = language;
    }
}
