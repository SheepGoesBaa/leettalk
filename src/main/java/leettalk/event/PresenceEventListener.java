package leettalk.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
public class PresenceEventListener {

	@Autowired
	private ParticipantRepository participantRepository;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@EventListener
	private void handleSessionConnected(SessionConnectedEvent event) {
		System.out.println("logged in m8");
		System.out.println("source: " + event.getSource().toString());
		System.out.println("message: " + event.getMessage().toString());
		System.out.println("username: " + event.getUser().getName());
		SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
		String username = headers.getUser().getName();

		LoginEvent loginEvent = new LoginEvent(username);
		//messagingTemplate.convertAndSend(loginDestination, loginEvent);
		
		// We store the session as we need to be idempotent in the disconnect event processing
		//participantRepository.add(headers.getSessionId(), loginEvent);
	}

}
