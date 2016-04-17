package leettalk;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepository extends JpaRepository<Command, Long> {
	
	Collection<Command> findByChatroomName(String name);
}
