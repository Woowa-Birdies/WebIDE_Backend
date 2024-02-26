package goorm.woowa.webide.chat.domain;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "chat")
public class Chat {
    @Id
    @Field(value = "_id", targetType = FieldType.OBJECT_ID)
    private String messageId;

    private Long roomId;
    // message 내용, 글자수 제한은 웹 소켓 컨테이너를 통해 설정
    private String message;
    // 채팅 작성자
    private String sender;
    // 채팅 읽은 유저 목록
    private Map<String, Boolean> readUser;

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updateAt;
}
