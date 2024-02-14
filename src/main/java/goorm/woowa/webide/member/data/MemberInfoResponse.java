package goorm.woowa.webide.member.data;

import lombok.*;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public class MemberInfoResponse {

    private String email;
    private String name;
    private String accessToken;
    private String refreshToken;

    public MemberInfoResponse(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public static MemberInfoResponse emailAndNameFrom(Map<String, String> googleUserInfo) {
        return new MemberInfoResponse(googleUserInfo.get("email"), googleUserInfo.get("name"));
    }
}
