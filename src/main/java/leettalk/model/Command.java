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

}
