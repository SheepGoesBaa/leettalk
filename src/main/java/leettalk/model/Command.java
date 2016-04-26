package leettalk.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Command {

	@JsonIgnore
	@ManyToOne
	private Chatroom chatroom;

	@Id
	@GeneratedValue
	private Long id;

	private String phrase;
	private String sourceCode;
	private Integer language;

	Command() { // for jpa

	}
	
	public Command(String phrase) {
		this.phrase = phrase;
	}
	
	public Command(Chatroom chatroom, String phrase) {
		this();
		this.chatroom = chatroom;
		this.phrase = phrase;
	}

	public Command(Chatroom chatroom, String phrase, String sourceCode, Integer language) {
		this.chatroom = chatroom;
		this.phrase = phrase;
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

	public String getPhrase() {
		return phrase;
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	@Override
	public String toString() {
		return "Command [chatroom=" + chatroom + ", id=" + id + ", phrase=" + phrase + ", sourceCode=" + sourceCode
				+ ", language=" + language + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chatroom == null) ? 0 : chatroom.hashCode());
		result = prime * result + ((phrase == null) ? 0 : phrase.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Command other = (Command) obj;
		if (chatroom == null) {
			if (other.chatroom != null)
				return false;
		} else if (!chatroom.equals(other.chatroom))
			return false;
		if (phrase == null) {
			if (other.phrase != null)
				return false;
		} else if (!phrase.equals(other.phrase))
			return false;
		return true;
	}

}
