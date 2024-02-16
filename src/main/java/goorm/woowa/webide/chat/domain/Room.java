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
    // 프로젝트 채팅방 1:1
    @OneToOne(fetch = FetchType.LAZY)
    private Project project;

    // admin (채팅 담당자)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member adminUser;
    // 공지 기능을 위한 chat collection messageId
    private String noticeId;

    @Builder
    public Room(Project project, Member adminUser, String noticeId) {
        this.project = project;
        this.adminUser = adminUser;
        this.noticeId = noticeId;
    }

}
