package goorm.woowa.webide.project.domain;


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

    // TODO: 유저 엔티티 생성되면 연관관계 설정 (다대일)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @Column(length = 128)
    private String name;
    @Enumerated(EnumType.STRING)
    private ProjectLanguage language;
    @Column(length = 128)
    private String ecsInfo;

    public Project(String name) {
        this.name = name;
    }

    public static Project toEntity(ProjectCreate projectCreate) {
        return new Project(projectCreate.getName());
    }

    public void update(ProjectUpdate projectUpdate) {
        this.name = projectUpdate.getName();
        this.language = projectUpdate.getLanguage();
    }
}
