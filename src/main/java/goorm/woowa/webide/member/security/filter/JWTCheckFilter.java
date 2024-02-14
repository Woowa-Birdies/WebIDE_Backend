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

        if (path.startsWith("/api/member/kakao") || path.startsWith("/api/member/google") || path.startsWith("/api/member/refresh")) {
            return true;
        }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeaderStr = request.getHeader("Authorization");
        try {
            //Bearer //7 jwt문자열
            //Bearer accestoken...
            String accessToken = authHeaderStr.substring(7);
            Map<String, Object> claims = JWTUtil.validateToken(accessToken);

            log.info("request={}", request.getRequestURI());
            log.info("claims={}", claims);

//            String email = (String) claims.get("email");
//            String pw = (String) claims.get("pw");
//            String nickname = (String) claims.get("nickname");
//            List<String> roleNames = (List<String>) claims.get("roleNames");
//
//            MemberDto memberDto = new MemberDto( email, pw, nickname, roleNames);
//
//            log.info("-----------------------------------");
//            log.info("memberDto={}", memberDto);
//            log.info("memberDto.getAuthorities()={}", memberDto.getAuthorities());
//
//            UsernamePasswordAuthenticationToken authenticationToken
//                    = new UsernamePasswordAuthenticationToken(memberDto, pw, memberDto.getAuthorities());
//
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
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
