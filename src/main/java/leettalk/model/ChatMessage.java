package leettalk.model;

public class ChatMessage {
	
	//private User user;
	
	private String message;
	
	public ChatMessage() {
		super();
	}
	
	public ChatMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ChatMessage [message=" + message + "]";
	}
	
}
