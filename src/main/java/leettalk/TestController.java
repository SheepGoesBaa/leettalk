package leettalk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@Autowired
	private ChatroomRepository chatroomRepository;
	
	@RequestMapping("/test/{roomName}")
	public Chatroom test(@PathVariable String roomName) {
		chatroomRepository.save(new Chatroom(roomName));
		return chatroomRepository.findByName(roomName).get();
	}
	
	
	
}
