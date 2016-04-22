package leettalk.model;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepository extends JpaRepository<Command, Long> {
	Collection<Command> findByChatroomName(String name);
	Optional<Command> findByPhraseAndChatroom(String phrase, Chatroom chatroom);
}
