package goorm.woowa.webide.member.controller;

import goorm.woowa.webide.member.util.CustomJWTException;
import goorm.woowa.webide.member.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ApiRefreshController {

    @RequestMapping("/api/member/refresh")
    public Map<String, Object> refresh(
            @RequestHeader("Authorization") String authHeader,
            String refreshToken
    ) {
        if (refreshToken == null) {
            throw new CustomJWTException("NULL_REFRESH");
        }

        if (authHeader == null || authHeader.length() < 7) {
            throw new CustomJWTException("INVALID_STRING");
        }

        String accessToken = authHeader.substring(7);

        // 접근 토큰 만료 여부 확인
        if (!checkExpiredToken(accessToken)) {
            return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
        }

        // refresh 토큰 검증
        Map<String, Object> claims = JWTUtil.validateToken(refreshToken);
        log.info("refresh ... claims={}", claims);
        String newAccessToken = JWTUtil.generateToken(claims, 30);
        String newRefreshToken = checkTime((Integer) claims.get("exp")) ? JWTUtil.generateToken(claims, 60 * 24) : refreshToken;

        return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);

    }

    // 리프레시 토큰의 시간이 1시간 미만으로 남았다면
    private boolean checkTime(Integer exp) {
        // JWT exp를 날짜로 변환
        Date expDate = new Date((long) exp * (1000));

        // 현재 시간과의 차이 계산 - 얼리 세컨즈
        long gap = expDate.getTime() - System.currentTimeMillis();

        // 분단위 계산
        long leftMin = gap / (1000 * 60);

        // 1시간도 안남았는지
        return leftMin < 60;
    }

    private boolean checkExpiredToken(String token) {
        try {
            JWTUtil.validateToken(token);
        } catch (CustomJWTException exception) {
            if (exception.getMessage().equals("Expired")) {
                return true;
            }
        }

        return false;
    }
}
