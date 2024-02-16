package goorm.woowa.webide.chat.repository;

import goorm.woowa.webide.chat.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>{
    Optional<List<Room>> findAllByAdminUser_Id(Long adminId);
}
