package leettalk;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Command {

	@ManyToOne
	Chatroom chatroom;

	@Id
	@GeneratedValue
	private Long id;

	private String sourceCode;
	private Integer language;

	Command() { // for jpa

	}
	
	public Command(Chatroom chatroom, String sourceCode, Integer language) {
		this.chatroom = chatroom;
		this.sourceCode = sourceCode;
		this.language = language;
	}

	public Chatroom getChatroom() {
		return chatroom;
	}

	public void setChatroom(Chatroom chatroom) {
		this.chatroom = chatroom;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public Integer getLanguage() {
		return language;
	}

	public void setLanguage(Integer language) {
		this.language = language;
	}

}
