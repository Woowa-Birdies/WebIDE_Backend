package goorm.woowa.webide.chat.repository;

import goorm.woowa.webide.chat.domain.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {
    Slice<Chat> findByRoomId(Long roomId, Pageable pageable);
}
