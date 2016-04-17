package leettalk;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Chatroom {

	@OneToMany
	private Set<Command> command;

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	public Chatroom() {
		command = new HashSet<>();
	}

	public Chatroom(String name) {
		this();
		this.name = name;
	}

	public Set<Command> getCommand() {
		return command;
	}

	public void setCommand(Set<Command> command) {
		this.command = command;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
