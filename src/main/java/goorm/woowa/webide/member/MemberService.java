package goorm.woowa.webide.member;

import goorm.woowa.webide.member.data.Member;
import goorm.woowa.webide.member.data.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedHashMap;
import java.util.Optional;

import static goorm.woowa.webide.member.data.MemberRole.USER;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberDto getKakaoMember(String accessToken) {
        // accessToken을 이용해서 사용자 정보 가져오기
        String nickname = getEmailFromKakaoAccessToken(accessToken);

        // 기존에 DB에 회원 정보가 있는 경우 / 없는 경우
        Optional<Member> resultMember = memberRepository.findByEmailAndNickname("kakao" + nickname, nickname);

        if (resultMember.isPresent()) {
            //return
            return MemberDto.from(resultMember.get());
        }

        Member socialMember = makeMember(nickname);
        memberRepository.save(socialMember);

        return MemberDto.from(socialMember);
    }

    private Member makeMember(String nickname) {
        String tempPassword = makeTempPassword();
        log.info("tempPassword={}", tempPassword);

        Member member = Member.builder()
                .email("kakao" + nickname)
                .pwd(passwordEncoder.encode(tempPassword))
                .nickname(nickname)
                .build();

        member.addRole(USER);

        return member;
    }

    private String getEmailFromKakaoAccessToken(String accessToken) {
        String kakaoGetUserURL = "https://kapi.kakao.com/v2/user/me";

        log.info("accessToken={}", accessToken);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(kakaoGetUserURL).build();

        ResponseEntity<LinkedHashMap> response =
                restTemplate.exchange(uriBuilder.toUri(), HttpMethod.GET, entity, LinkedHashMap.class);

        log.info("카카오에서 받은 response={}", response);

        LinkedHashMap<String, LinkedHashMap> bodyMap = response.getBody();
        LinkedHashMap<String, String> kakaoAccount = bodyMap.get("properties");
        String nickname = kakaoAccount.get("nickname");
        log.info("nickname={}", nickname);

        log.info("--------------------------------");
        log.info("bodyMap={}", bodyMap);

        return nickname;
    }

    private String makeTempPassword() {
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < 10; i++) {
            buffer.append((char) ((int) (Math.random() * 55) * 65));
        }
        return buffer.toString();
    }

}
