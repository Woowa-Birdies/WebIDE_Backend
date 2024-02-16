package goorm.woowa.webide.project.domain;


import goorm.woowa.webide.candidate.domain.Candidate;
import goorm.woowa.webide.common.domain.BaseTimeEntity;
import goorm.woowa.webide.member.data.Member;
import goorm.woowa.webide.project.domain.dto.ProjectCreate;
import goorm.woowa.webide.project.domain.dto.ProjectUpdate;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Entity
@DynamicUpdate
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

    public Project(String name, Long problemId, Member member) {
        this.name = name;
        this.problemId = problemId;
        this.member = member;
    }

    public static Project toEntity(ProjectCreate projectCreate, Member member) {
        return new Project(projectCreate.getName(),
                projectCreate.getProblemId(),
                member
        );
    }

    public void update(ProjectUpdate projectUpdate) {
        this.name = projectUpdate.getName();
    }

    public void registerCandidate(Candidate candidate, ProjectLanguage language) {
        this.candidateId = candidate.getId();
        this.language = language;
    }
}
