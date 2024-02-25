package goorm.woowa.webide.member.data;

import goorm.woowa.webide.project.domain.Project;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "roleList")
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String email;
    private String pwd;
    private String nickname;
    private String profile;
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private List<MemberRole> roleList = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Project> projects = new ArrayList<>();

    public void addRole(MemberRole role) {
        this.getRoleList().add(role);
    }

    public void clearRole() {
        this.getRoleList().clear();
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changePwd(String pwd) {
        this.pwd = pwd;
    }
}
