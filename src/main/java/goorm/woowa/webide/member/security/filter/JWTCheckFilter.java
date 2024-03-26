package goorm.woowa.webide.member.security.filter;

import com.google.gson.Gson;
import goorm.woowa.webide.member.data.MemberDto;
import goorm.woowa.webide.member.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

// 모든 요청에 동작하는 필터
@Slf4j
public class JWTCheckFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String path = request.getRequestURI();

        if (path.startsWith("/api/member/kakao")
                || path.startsWith("/api/member/google")
                || path.startsWith("/api/member/refresh")
                || path.startsWith("/ide")
                || path.startsWith("/candidate")
                || path.startsWith("/ws")
                || path.startsWith("/actuator")) {
            return true;
        }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeaderStr = request.getHeader("Authorization");
        System.out.println("헤더에서 : " + authHeaderStr);
        try {
            //Bearer //7 jwt문자열
            //Bearer accestoken...
            String accessToken = authHeaderStr.substring(7);
            System.out.println("토큰:"+accessToken);
            Map<String, Object> claims = JWTUtil.validateToken(accessToken);

            log.info("request={}", request.getRequestURI());
            log.info("claims={}", claims);

            filterChain.doFilter(request, response);
            System.out.println("테스트 성공");
            
        } catch (Exception e) {
            log.error("jwt check error={}", e.getMessage());

            Gson errorResponse = new Gson();
            String msg = errorResponse.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));

            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            writer.println(msg);
            writer.close();
        }


    }
}
