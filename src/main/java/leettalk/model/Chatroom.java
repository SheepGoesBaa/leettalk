package leettalk.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"name"}))
public class Chatroom {

	@OneToMany(mappedBy = "chatroom")
	private Set<Command> commands = new HashSet<>();

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	Chatroom() {
	}

	public Chatroom(String name) {
		this();
		this.name = name;
	}

	public Set<Command> getCommands() {
		return commands;
	}

	public void setCommands(Set<Command> command) {
		this.commands = command;
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

	@Override
	public String toString() {
		return "Chatroom [commands=" + commands + ", id=" + id + ", name=" + name + "]";
	}

}
