package goorm.woowa.webide.member.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateDto {

    private String email;
    private String pwd;
    private String nickname;
}
