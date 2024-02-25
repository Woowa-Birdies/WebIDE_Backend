package goorm.woowa.webide.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @MessageMapping("/chat")    // 클라이언트 -> 서버 "/pub/chat"
    @SendTo("/sub/chat")        // 구독자에게 전달
    public transmitMessage chat(receiveMessage message) throws Exception {
        return new transmitMessage(message.getMessage());
        // transmitMessage, receiveMessage : 테스트용
    }
}
