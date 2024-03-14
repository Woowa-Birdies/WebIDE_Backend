package goorm.woowa.webide.chat.service;

import goorm.woowa.webide.chat.domain.Chat;
import goorm.woowa.webide.chat.domain.dto.ChatDto;
import goorm.woowa.webide.chat.domain.dto.ChatResposneDto;
import goorm.woowa.webide.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
public interface ChatService {

    Slice<ChatResposneDto> getChatList(Long roomId, int page, int size);
    ChatResposneDto saveChat(ChatDto chatDto);
    void deleteChat(String messageId);
}
