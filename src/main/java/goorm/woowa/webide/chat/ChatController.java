package goorm.woowa.webide.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

public class ChatController {
    @MessageMapping("/chat")    // 클라이언트 -> 서버 "/pub/chat"
    @SendTo("/sub/chat")        // 구독자에게 전달
    public transmitMessage chat(receiveMessage message) throws Exception {
        System.out.println("메세지 : " + message);
        return new transmitMessage(message.getMessage());
        // transmitMessage, receiveMessage : 테스트용
    }
}
