package goorm.woowa.webide.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    //todo jwt intercepter
    //todo error handler
    // custom shake handler

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        // /sub에게 모두 전달
        registry.enableSimpleBroker("/sub");

        // /pub 시작 메시지가 message-handling methods로 라우팅
        registry.setApplicationDestinationPrefixes("/pub");

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*").withSockJS();
        //todo: errorhandler, interceptor, handshake handler 추가
    }

    //todo : 웹소켓 커넥션, 데이터 크기 설정 컨테이너 관리 bean 생성
}
