package goorm.woowa.webide.chat;

import goorm.woowa.webide.chat.domain.Chat;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @MessageMapping("/chat/{projectIdParam}")    // 클라이언트 -> 서버 "/pub/chat"
    @SendTo("/sub/chat/{projectIdParam}")        // 구독자에게 전달
    public Chat chat(Chat message) throws Exception {
        Chat chat = new Chat();
        chat.setMessage(message.getMessage());
        return chat;
    }
}
