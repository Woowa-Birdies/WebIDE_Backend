package goorm.woowa.webide.member.data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.*;
import java.util.stream.Collectors;

public class MemberDto extends User {

    private String email, pwd, nickname;
    private List<String> roleNames = new ArrayList<>();


    public MemberDto(String username, String password, String nickname, List<String> roleNames) {
        super(
                username,
                password,
                roleNames.stream().map(str -> new SimpleGrantedAuthority("ROLE_" + str)).collect(Collectors.toList())

        );

        this.email = email;
        this.pwd = pwd;
        this.nickname = nickname;
        this.roleNames = roleNames;
    }

    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("email", email);
        dataMap.put("pwd", pwd);
        dataMap.put("nickname", nickname);
        dataMap.put("roleNames", roleNames);

        return dataMap;
    }

    public static MemberDto from(Member member) {
        return new MemberDto(
                member.getEmail(),
                member.getPwd(),
                member.getNickname(),
                member.getRoleList().stream().map(memberRole -> memberRole.name()).collect(Collectors.toList())
        );
    }
}
