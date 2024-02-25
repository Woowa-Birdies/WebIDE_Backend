package goorm.woowa.webide.common.handler;
import goorm.woowa.webide.member.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class JwtInterceptor implements ChannelInterceptor {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret:1234567890123456789012345678901234567890}")
    private String secretKey;

    @Override
    public Message<?> preSend(Message<?> message, org.springframework.messaging.MessageChannel channel) {
        final StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.CONNECT == accessor.getCommand()) {
            final String header = accessor.getFirstNativeHeader(AUTHORIZATION_HEADER);
//            // token이 없거나 유효하지 않은 경우 에러
//            if (header == null || !header.startsWith(BEARER_PREFIX) || JWTUtil.validateToken(header.substring(BEARER_PREFIX.length())).isEmpty()) {
//                throw new IllegalArgumentException("No JWT token found in request headers");
//                //todo custom exception 4010 NOT AUTHORIZED
//            }
            // 토큰이 있을 때만 검증하도록 변경
            if (header != null && header.startsWith(BEARER_PREFIX)) {
                try {
                    // 토큰이 유효한지 검증
                    JWTUtil.validateToken(header.substring(BEARER_PREFIX.length()));
                } catch (Exception e) {
                    log.error("Error validating JWT token: {}", e.getMessage());
                    // 예외 발생 시 로그만 출력하고 계속 진행
                }
            }
        }
        return message;
    }
}