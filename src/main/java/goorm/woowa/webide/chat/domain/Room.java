package goorm.woowa.webide.chat.domain;

import goorm.woowa.webide.common.domain.BaseTimeEntity;
import goorm.woowa.webide.member.data.Member;
import goorm.woowa.webide.project.domain.Project;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @OneToOne(mappedBy = "room", fetch = FetchType.LAZY)
    private Project project;

    @OneToOne(mappedBy = "room", fetch = FetchType.LAZY)
    private Member adminUser;

    private String noticeId;

    @Builder
    public Room(Project project, Member adminUser, String noticeId) {
        this.project = project;
        this.adminUser = adminUser;
        this.noticeId = noticeId;
    }

}
