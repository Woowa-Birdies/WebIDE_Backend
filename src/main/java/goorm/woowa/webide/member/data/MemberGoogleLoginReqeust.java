package goorm.woowa.webide.member.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberGoogleLoginReqeust {
    private String accessToken;
    private String scope;
}

