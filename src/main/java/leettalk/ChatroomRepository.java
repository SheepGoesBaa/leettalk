package leettalk;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {
	Optional<Chatroom> findByName(String name);
}
