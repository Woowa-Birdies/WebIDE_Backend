package goorm.woowa.webide.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @MessageMapping("/chat")    // 클라이언트 -> 서버 "/pub/chat"
    @SendTo("/sub/chat")        // 구독자에게 전달
    public transmitMessage chat(receiveMessage message) throws Exception {
        Thread.sleep(5000);
        return new transmitMessage("서버에서 전송 : " + message.getMessage());
        // transmitMessage, receiveMessage : 테스트용
    }
}
