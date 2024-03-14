package goorm.woowa.webide.chat.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatResposneDto {
    private final String messageId;
    private final Long roomId;
    private final String message;
    private final String sender;
    private final LocalDateTime createAt;
    private final LocalDateTime updateAt;

    @Builder
    public ChatResposneDto(String messageId, Long roomId, String message, String sender, LocalDateTime createAt, LocalDateTime updateAt) {
        this.messageId = messageId;
        this.roomId = roomId;
        this.message = message;
        this.sender = sender;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
