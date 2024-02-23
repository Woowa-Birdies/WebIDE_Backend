package goorm.woowa.webide.member.controller;

import goorm.woowa.webide.member.MemberService;
import goorm.woowa.webide.member.data.*;
import goorm.woowa.webide.member.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class SocialController {

    private final MemberService memberService;

    @PostMapping("/kakao")
    public Map<String, Object> getMemberFromKakao(@RequestBody String accessToken) {

        MemberDto member = memberService.getKakaoMember(accessToken);

        Map<String, Object> claims = member.getClaims();

        claims.put("accessToken", JWTUtil.generateToken(claims, 10));
        claims.put("refreshToken", JWTUtil.generateToken(claims, 60 * 24));

        return claims;
    }

    @PostMapping("/google")
    public Map<String, Object> getMemberFormGoogle(@RequestBody String accessToken) {

        MemberDto googleMember = memberService.getGoogleMember(accessToken);

        Map<String, Object> claims = googleMember.getClaims();

        claims.put("accessToken", JWTUtil.generateToken(claims, 10));
        claims.put("refreshToken", JWTUtil.generateToken(claims, 60 * 24));

        return claims;
    }

    @PutMapping
    public Map<String, Object> update(@RequestBody MemberUpdateDto memberUpdateDto) {
        log.info("update member={}", memberUpdateDto);
        log.info("update member atk={}", memberUpdateDto.getAccessToken());
        log.info("update member rtk={}", memberUpdateDto.getRefreshToken());

        MemberDto member = memberService.updateMember(memberUpdateDto);

        Map<String, Object> claims = member.getClaims();
        claims.put("accessToken", JWTUtil.generateToken(claims, 10));
        claims.put("refreshToken", JWTUtil.generateToken(claims, 60 * 24));

        log.info("after update. member atk={}", claims.get("accessToken"));
        log.info("after update. member rtk={}", claims.get("refreshToken"));

        return claims;
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> delete(String email) {
        log.info("hellolhkldjfkljskdlfj={}", email);
        memberService.delete(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
