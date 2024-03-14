package goorm.woowa.webide.chat.service.impl;

import goorm.woowa.webide.chat.domain.Chat;
import goorm.woowa.webide.chat.domain.dto.ChatDto;
import goorm.woowa.webide.chat.domain.dto.ChatResposneDto;
import goorm.woowa.webide.chat.repository.ChatRepository;
import goorm.woowa.webide.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    @Override
    public Slice<ChatResposneDto> getChatList(Long roomId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createAt"));
        Slice<Chat> chatSlice = chatRepository.findByRoomId(roomId, pageable);
        return chatSlice.map(chat -> ChatResposneDto.builder()
                .messageId(chat.getMessageId())
                .roomId(chat.getRoomId())
                .message(chat.getMessage())
                .sender(chat.getSender())
                .createAt(chat.getCreateAt())
                .updateAt(chat.getUpdateAt())
                .build());
    }

    @Override
    @Transactional
    public ChatResposneDto saveChat(ChatDto chatDto) {
        Chat chat = Chat.builder()
                .roomId(chatDto.getRoomId())
                .message(chatDto.getMessage())
                .sender(chatDto.getSender())
                .build();
        Chat savedChat = chatRepository.save(chat);
        return ChatResposneDto.builder()
                .messageId(savedChat.getMessageId())
                .roomId(savedChat.getRoomId())
                .message(savedChat.getMessage())
                .sender(savedChat.getSender())
                .createAt(savedChat.getCreateAt())
                .updateAt(savedChat.getUpdateAt())
                .build();
    }

    @Override
    @Transactional
    public void deleteChat(String messageId) {
        chatRepository.deleteById(messageId);
    }


}
