package goorm.woowa.webide.chat.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatDto {
    private final Long roomId;
    private final String message;
    private final String sender;
    private final LocalDateTime createAt;

    @Builder
    public ChatDto(Long roomId, String message, String sender, LocalDateTime createAt) {
        this.roomId = roomId;
        this.message = message;
        this.sender = sender;
        this.createAt = createAt;
    }

}
