package goorm.woowa.webide.member.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
public class MemberDto extends User {

    private Long memberId;
    private String email, pwd, profile, nickname;
    private List<String> roleNames = new ArrayList<>();


    public MemberDto(Long id,
                     String email,
                     String pwd,
                     String profile,
                     String nickname,
                     List<String> roleNames) {
        super(
                email,
                pwd,
                roleNames.stream().map(str -> new SimpleGrantedAuthority("ROLE_" + str)).collect(Collectors.toList())
        );

        this.memberId = id;
        this.email = email;
        this.pwd = pwd;
        this.profile = profile;
        this.nickname = nickname;
        this.roleNames = roleNames;
    }

    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("memberId", memberId);
        dataMap.put("email", email);
        dataMap.put("pwd", pwd);
        dataMap.put("profile", profile);
        dataMap.put("nickname", nickname);
        dataMap.put("roleNames", roleNames);

        log.info("get claims={}", dataMap);


        return dataMap;
    }

    public static MemberDto from(Member member) {
        return new MemberDto(
                member.getId(),
                member.getEmail(),
                member.getPwd(),
                member.getProfile(),
                member.getNickname(),
                member.getRoleList().stream().map(memberRole -> memberRole.name()).collect(Collectors.toList())
        );
    }
}
