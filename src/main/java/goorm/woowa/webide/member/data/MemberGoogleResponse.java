package goorm.woowa.webide.member.data;

import lombok.Data;

@Data
public class MemberGoogleResponse {
    private Long id;
    private String email;
    private boolean verified_email;
    private String name;
    private String given_name;
    private String family_name;
    private String picture;
    private String locale;
}
