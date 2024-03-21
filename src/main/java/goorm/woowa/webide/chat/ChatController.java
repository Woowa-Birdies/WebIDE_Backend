package goorm.woowa.webide.chat;

import goorm.woowa.webide.chat.domain.Chat;
import goorm.woowa.webide.chat.repository.ChatRepository;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class ChatController {
    private ChatRepository chatRepository;
    private ChatController(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @MessageMapping("/chat/{projectIdParam}")    // 클라이언트 -> 서버 "/pub/chat"
    @SendTo("/sub/chat/{projectIdParam}")        // 구독자에게 전달
    public Chat chat(Chat message, @DestinationVariable String projectIdParam) throws Exception {
        System.out.println("message:"+message.getSender());
        Chat chat = new Chat();
        Long roomId = Long.valueOf(projectIdParam);
        chat.setMessage(message.getMessage());
        chat.setRoomId(roomId);
        chat.setSender(message.getSender());
        chatRepository.save(chat);
        return chat;
    }
}
