package goorm.woowa.webide.member.controller;

import goorm.woowa.webide.member.MemberService;
import goorm.woowa.webide.member.data.MemberDto;
import goorm.woowa.webide.member.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SocialController {

    private final MemberService memberService;

    // /api -> jwt 필터 안 고치게 하려고 붙임
    @GetMapping("/api/oauth/kakao")
    public Map<String, Object> getMemberFromKakao(String accessToken) {
        log.info("accessToken from kakao={}", accessToken);

        MemberDto member = memberService.getKakaoMember(accessToken);

        Map<String, Object> claims = member.getClaims();

        String jwtAccessToken = JWTUtil.generateToken(claims, 10);
        String jwtRefreshToken = JWTUtil.generateToken(claims, 60 * 24);

        claims.put("accessToken", jwtAccessToken);
        claims.put("refreshToken", jwtRefreshToken);

        return claims;
    }
}
