package goorm.woowa.webide.member.controller;

import goorm.woowa.webide.member.MemberService;
import goorm.woowa.webide.member.data.MemberDto;
import goorm.woowa.webide.member.data.MemberGoogleLoginReqeust;
import goorm.woowa.webide.member.data.MemberInfoResponse;
import goorm.woowa.webide.member.data.MemberUpdateDto;
import goorm.woowa.webide.member.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class SocialController {

    private final MemberService memberService;

    // /api -> jwt 필터 안 고치게 하려고 붙임
    @GetMapping("/kakao")
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

    @PostMapping("/google")
    public Map<String, Object> getMemberFormGoogle(@RequestBody MemberGoogleLoginReqeust memberLoginReqeust) {
        log.info("google login request={}", memberLoginReqeust);

        MemberDto googleMember = memberService.getGoogleMember(memberLoginReqeust.getAccessToken(), memberLoginReqeust.getScope());
        log.info("googleMember at controller ={}", googleMember);

        Map<String, Object> claims = googleMember.getClaims();

        String jwtAccessToken = JWTUtil.generateToken(claims, 10);
        String jwtRefreshToken = JWTUtil.generateToken(claims, 60 * 24);

        claims.put("accessToken", jwtAccessToken);
        claims.put("refreshToken", jwtRefreshToken);

        return claims;
    }

    @PutMapping("/update")
    public Map<String, Object> update(@RequestBody MemberUpdateDto memberUpdateDto) {
        log.info("update member={}", memberUpdateDto);

        memberService.updateMember(memberUpdateDto);

        return Map.of("result", "modified");
    }
}
