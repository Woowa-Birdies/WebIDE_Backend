package goorm.woowa.webide.config;

import goorm.woowa.webide.common.handler.JwtInterceptor;
import goorm.woowa.webide.common.handler.MyHandler;
import goorm.woowa.webide.common.handler.WebSocketHandshakeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final JwtInterceptor jwtInterceptor;
    //todo error handler

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(1024 * 1024);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        // 해당 파라미터의 접두사가 붙은 목적지(구독자)에게 메세지
        registry.enableSimpleBroker("/sub");

        // `/pub` 시작 메시지가 message-handling methods로 라우팅
        registry.setApplicationDestinationPrefixes("/pub");

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .addInterceptors(new WebSocketHandshakeInterceptor())
                .setAllowedOriginPatterns("*");

        //todo: errorhandler, handshake handler 추가
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(jwtInterceptor);
    }
}
