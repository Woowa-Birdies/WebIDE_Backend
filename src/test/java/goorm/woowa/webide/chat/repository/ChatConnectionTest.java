package goorm.woowa.webide.chat.repository;

import goorm.woowa.webide.chat.domain.Chat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ChatConnectionTest {
    @Autowired
    private ChatRepository chatRepository;

    @Test
    public void testSaveChat() {
        Chat chat = Chat.builder()
                .roomId(1L)
                .message("Hello")
                .sender("user1")
                .build();
        Chat savedChat = chatRepository.save(chat);
        assertNotNull(savedChat.getMessageId());
        // 추가 검증
    }
}
